package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.ContentTypeDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.DifferenceInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.events.FileChangedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnsupportedContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TikaService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by piotr on 06.01.17.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class FilesMetadataServiceImpl implements FilesMetadataService {
    private final static Logger LOGGER = LoggerFactory.getLogger(FilesMetadataServiceImpl.class);

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final VersionService versionService;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final TikaService tikaService;

    @NonNull
    private final UserService userService;

    @Setter
    private ApplicationEventPublisher applicationEventPublisher;

    private ContentType getContentType(byte[] file)
            throws UnsupportedContentType {
        String contentType = tikaService.detectMediaType(file);
        return ContentType.fromName(contentType)
                .orElseThrow(() -> new UnsupportedContentType(contentType));
    }

    @Override
    @Transactional(rollbackFor = {UnsupportedContentType.class, ResourceNotFoundException.class})
    public UUID createNewFileFromForm(NewFileForm formData, final UUID projectId, final UUID taskId)
            throws UnsupportedContentType, ResourceNotFoundException {
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId.toString()));
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setTaskId(taskId);
        fileMetadata.setFileId(UUID.randomUUID());
        fileMetadata.setName(formData.getName());
        fileMetadata.setTaskName(task.getName());
        fileMetadata.setDescription(formData.getDescription());
        try {
            fileMetadata.setContentType(getContentType(formData.getFile().getBytes()));
        } catch (IOException e) {
            LOGGER.error("Input/output exception occurred during getBytes method", e);
            throw new RuntimeException(e);
        }
        fileMetadata.setConfirmed(false);
        fileMetadata.setMarkedToConfirm(false);
        fileMetadata.setCreationDate(new Date());
        fileMetadata.setNumberOfVersions(1);

        VersionSummary versionSummary = new VersionSummary();
        UserSummary modificationAuthor = new UserSummary(userService.getCurrentUser());
        versionSummary.setModificationAuthor(modificationAuthor);
        versionSummary.setSaveDate(new Date());
        versionSummary.setVersion(formData.getVersionString());
        fileMetadata.setLatestVersion(versionSummary);

        fileMetadata = fileMetadataRepository.save(fileMetadata);
        UUID fileId = fileMetadata.getFileId();

        versionService.createInitVersionOfFile(formData, fileId);

        FileChangedEvent event = new FileChangedEvent(this, projectId, taskId, fileId);
        applicationEventPublisher.publishEvent(event);

        return fileId;
    }

    @Override
    @Transactional(readOnly = true)
    public FileMetadataDTO getFileMetadataDTO(UUID taskId, UUID fileId) throws FileNotFoundException {
        FileMetadata fileMetadata = getFileMetadata(taskId, fileId);
        List<VersionInfoDTO> versions = versionRepository.findAllByFileId(fileId)
                .map(this::toVersionInfoDTO)
                .collect(Collectors.toList());
        return new FileMetadataDTO(
                fileMetadata.getFileId().toString(),
                fileMetadata.getName(),
                fileMetadata.getDescription(),
                fileMetadata.getContentType().name(),
                fileMetadata.getContentType().getExtension(),
                fileMetadata.isConfirmed(),
                fileMetadata.isMarkedToConfirm(),
                getTimestamp(fileMetadata.getCreationDate()),
                getTimestamp(fileMetadata.getLatestVersion().getSaveDate()),
                VersionSummaryDTO.fromVersionSummaryEntity(fileMetadata.getLatestVersion()),
                fileMetadata.getNumberOfVersions(),
                versions
        );
    }

    @Override
    @Transactional
    public FileMetadata getFileMetadata(UUID taskId, UUID fileId) throws FileNotFoundException {
        return fileMetadataRepository.findOneByTaskIdAndFileId(taskId, fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }

    private VersionInfoDTO toVersionInfoDTO(Version version) {
        return new VersionInfoDTO(
                version.getMessage(),
                UserInfoDTO.fromUserSummary(version.getAuthor()),
                version.getSaveDate().getTime(),
                version.getVersionString(),
                "",
                version.getDifferences()
                        .stream()
                        .map(DifferenceInfoDTO::fromDifference)
                        .collect(Collectors.toList())
        );
    }

    private Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    @Override
    @Transactional
    public void markFileToConfirm(UUID taskId, UUID fileId) {
        fileMetadataRepository.updateMarkToConfirmTrueByTaskIdAndFileId(taskId, fileId);
    }

    @Override
    public boolean hasContentTypeAs(UUID taskId, UUID fileId, byte[] file) throws FileNotFoundException {
        FileMetadata fileMetadata = getFileMetadata(taskId, fileId);
        ContentType contentType;
        try {
            contentType = getContentType(file);
        } catch (UnsupportedContentType e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return false;
        }
        return contentType.equals(fileMetadata.getContentType());
    }

    @Override
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void confirmFile(UUID taskId, UUID fileId) {
        fileMetadataRepository.updateConfirmedTrueByTaskIdAndFileId(taskId, fileId);
    }

    @Override
    @Transactional
    public void deleteFile(UUID projectId, UUID taskId, UUID fileId) {
        versionRepository.deleteAllByFileId(fileId);
        fileMetadataRepository.deleteFileMetadataByTaskIdAndFileId(taskId, fileId);
        applicationEventPublisher.publishEvent(new FileChangedEvent(this, projectId, taskId, fileId));
    }

    @Override
    public String getFileName(UUID taskId, UUID fileId) throws FileNotFoundException {
        return fileMetadataRepository.findOneByTaskIdAndFileId(taskId, fileId)
                .map(metadata -> metadata.getName() + "." + metadata.getContentType().getExtension())
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public ContentTypeDTO getContentType(UUID taskId, UUID fileId) throws FileNotFoundException {
        return fileMetadataRepository.findOneByTaskIdAndFileId(taskId, fileId)
                .map(fileMetadata -> {
                    ContentType contentType = fileMetadata.getContentType();
                    return new ContentTypeDTO(contentType.getExtension(), contentType.getName());
                })
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }
}
