package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.ContentTypeDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.DifferenceInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnknownContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TikaService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private final UserService userService;

    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final TikaService tikaService;

    private ContentType getContentType(byte[] file)
            throws UnknownContentType {
        String contentType = tikaService.detectMediaType(file);
        return ContentType.fromName(contentType)
                .orElseThrow(() -> new UnknownContentType(contentType));
    }

    @Override
    @Transactional(rollbackFor = {UnknownContentType.class, ResourceNotFoundException.class})
    public Long createNewFileFromForm(NewFileForm formData, Long taskId)
            throws UnknownContentType, ResourceNotFoundException {
        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId.toString());
        }
        User currentUser = userService.getCurrentUser();
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setName(formData.getName());
        fileMetadata.setDescription(formData.getDescription());
        fileMetadata.setConfirmed(false);
        fileMetadata.setMarkedToConfirm(false);
        fileMetadata.setCreationDate(getNowTimestamp());
        fileMetadata.setTask(task);
        try {
            fileMetadata.setContentType(getContentType(formData.getFile().getBytes()));
        } catch (IOException e) {
            LOGGER.error("Input/output exception occurred during getBytes method", e);
            throw new RuntimeException(e);
        }
        VersionSummary versionSummary = new VersionSummary(formData.getVersionString(), getNowTimestamp(), currentUser);
        fileMetadata.setLatestVersion(versionSummary);
        fileMetadata = fileMetadataRepository.save(fileMetadata);

        versionService.createInitVersionOfFile(formData, fileMetadata);

        return fileMetadata.getId();
    }

    private Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    @Override
    @Transactional(readOnly = true)
    public FileMetadataDTO getFileMetadataDTO(Long fileId) throws FileNotFoundException {
        FileMetadata fileMetadata = getFileMetadata(fileId);
        List<Version> versions = versionRepository.findAllByFileId(fileId);
        return new FileMetadataDTO(
                fileMetadata.getId().toString(),
                fileMetadata.getName(),
                fileMetadata.getDescription(),
                fileMetadata.getContentType().getName(),
                fileMetadata.getContentType().getExtension(),
                fileMetadata.getConfirmed(),
                fileMetadata.getMarkedToConfirm(),
                fileMetadata.getCreationDate(),
                fileMetadata.getLatestVersion().getSaveDate(),
                VersionSummaryDTO.fromVersionSummaryEntity(fileMetadata.getLatestVersion()),
                versions.size(),
                mapToDTOsWithOutPreviousVersionsString(versions)
        );
    }

    private FileMetadata getFileMetadata(Long fileId) throws FileNotFoundException {
        FileMetadata fileMetadata = fileMetadataRepository.findOne(fileId);
        if (fileMetadata == null) {
            throw new FileNotFoundException(fileId.toString());
        }
        return fileMetadata;
    }

    private List<VersionInfoDTO> mapToDTOsWithOutPreviousVersionsString(List<Version> versions) {
        return versions.stream()
                .map(version -> new VersionInfoDTO(
                        version.getMessage(),
                        UserInfoDTO.fromUserSummary(version.getAuthor()),
                        version.getSaveDate(),
                        version.getVersionString(),
                        "",
                        mapDifferencesToDTOs(version.getDifferences())
                )).collect(Collectors.toList());
    }

    private List<DifferenceInfoDTO> mapDifferencesToDTOs(List<Difference> differences) {
        if (differences == null) {
            return Collections.emptyList();
        }
        return differences.stream()
                .map(difference -> new DifferenceInfoDTO(
                        difference.getPreviousSectionStart(),
                        difference.getPreviousSectionSize(),
                        difference.getNewSectionStart(),
                        difference.getNewSectionSize(),
                        difference.getDifferenceType()
                )).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void markFileToConfirm(Long fileId) throws FileNotFoundException {
        int marked = fileMetadataRepository.markToConfirm(fileId);
        if (marked != 1) {
            throw new FileNotFoundException(fileId.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasContentTypeAs(Long fileId, byte[] file) throws FileNotFoundException {
        if (!fileMetadataRepository.exists(fileId)) {
            throw new FileNotFoundException(fileId.toString());
        }
        try {
            ContentType contentType = getContentType(file);
            return fileMetadataRepository.existsByIdAndContentType(fileId, contentType);
        } catch (UnknownContentType e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void confirmFile(Long fileId) throws FileNotFoundException {
        int confirmed = fileMetadataRepository.setConfirmedTrue(fileId);
        if (confirmed != 1) {
            throw new FileNotFoundException(fileId.toString());
        }
    }

    @Override
    @Transactional
    public void deleteFile(Long fileId) {
        versionRepository.deleteAllByFileId(fileId);
        fileMetadataRepository.delete(fileId);
    }

    @Override
    @Transactional(readOnly = true)
    public String getFileName(Long fileId) throws FileNotFoundException {
        FileMetadata fileMetadata = fileMetadataRepository.findOne(fileId);
        if (fileMetadata == null) {
            throw new FileNotFoundException(fileId.toString());
        }
        return fileMetadata.getName().concat(".").concat(fileMetadata.getContentType().getExtension());
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfFiles(Task task) {
        return fileMetadataRepository.countDistinctByTask(task);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfFiles(Project project) {
        return fileMetadataRepository.countDistinctByTask_Project(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileSummaryDTO> getLastModifiedFileSummary(Task task) {
        return fileMetadataRepository.findFirstByTaskOrderByLatestVersion_SaveDateDesc(task)
                .map(fileMetadata -> new FileSummaryDTO(
                        fileMetadata.getName(),
                        fileMetadata.getLatestVersion().getSaveDate(),
                        fileMetadata.getLatestVersion().getModificationAuthor().getFullName(),
                        task.getName()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileSummaryDTO> getLastModifiedFileSummary(Project project) {
        return fileMetadataRepository.findFirstByTask_ProjectOrderByLatestVersion_SaveDateDesc(project)
                .map(fileMetadata -> new FileSummaryDTO(
                        fileMetadata.getName(),
                        fileMetadata.getLatestVersion().getSaveDate(),
                        fileMetadata.getLatestVersion().getModificationAuthor().getFullName(),
                        fileMetadata.getTask().getName()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public ContentTypeDTO getContentType(final Long fileId) throws FileNotFoundException {
        return fileMetadataRepository.findContentTypeById(fileId)
                .map(contentType -> new ContentTypeDTO("." + contentType.getExtension(), contentType.getName()))
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }
}
