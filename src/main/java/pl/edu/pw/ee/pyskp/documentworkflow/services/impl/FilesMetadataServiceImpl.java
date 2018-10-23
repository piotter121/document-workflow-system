package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.ContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.ContentTypeDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnknownContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TikaService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.FileCreatedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.FileDeletedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.VersionCreatedEvent;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by piotr on 06.01.17.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FilesMetadataServiceImpl implements FilesMetadataService {
    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final VersionService versionService;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final ApplicationEventPublisher applicationEventPublisher;

    @NonNull
    private final TikaService tikaService;

    private ContentType getContentType(byte[] file) throws UnknownContentType {
        String contentType = tikaService.detectMediaType(file);
        return ContentType.fromName(contentType)
                .orElseThrow(() -> new UnknownContentType(contentType));
    }

    @Override
    @Transactional(rollbackFor = {UnknownContentType.class, ResourceNotFoundException.class})
    public ObjectId createNewFileFromForm(NewFileForm formData, ObjectId projectId, ObjectId taskId)
            throws UnknownContentType, ResourceNotFoundException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId.toString()));

        FileMetadata newFile = new FileMetadata();
        newFile.setTask(task);
        newFile.setName(formData.getName());
        newFile.setDescription(formData.getDescription());
        newFile.setConfirmed(false);
        newFile.setMarkedToConfirm(false);
        newFile.setCreationDate(new Date());
        try {
            newFile.setContentType(getContentType(formData.getFile().getBytes()));
        } catch (IOException e) {
            log.error("Input/output exception occurred during getBytes method", e);
            throw new RuntimeException(e);
        }
        newFile = fileMetadataRepository.save(newFile);

        Version initVersion = versionService.createInitVersionOfFile(formData, newFile);

        newFile.setLatestVersion(initVersion);
        newFile.setNumberOfVersions(1);
        fileMetadataRepository.save(newFile);

        applicationEventPublisher.publishEvent(new FileCreatedEvent(this, newFile));

        return newFile.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public FileMetadataDTO getFileMetadataDTO(ObjectId fileId) throws FileNotFoundException {
        FileMetadata fileMetadata = getFileMetadata(fileId);
        List<Version> versions = versionRepository.findByFile(fileMetadata);
        return FileMetadataDTO.fromFileMetadataAndVersions(fileMetadata, versions);
    }

    private FileMetadata getFileMetadata(ObjectId fileId) throws FileNotFoundException {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }

    @Override
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void markFileToConfirm(ObjectId fileId) throws FileNotFoundException {
        FileMetadata file = getFileMetadata(fileId);
        file.setMarkedToConfirm(true);
        fileMetadataRepository.save(file);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasContentTypeAs(ObjectId fileId, byte[] file) throws FileNotFoundException {
        try {
            FileMetadata fileMetadata = getFileMetadata(fileId);
            return getContentType(file).equals(fileMetadata.getContentType());
        } catch (UnknownContentType e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void confirmFile(ObjectId fileId) throws FileNotFoundException {
        FileMetadata fileToConfirm = getFileMetadata(fileId);
        fileToConfirm.setConfirmed(true);
        fileMetadataRepository.save(fileToConfirm);
    }

    @Override
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void deleteFile(ObjectId fileId) throws FileNotFoundException {
        FileMetadata fileToDelete = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
        versionRepository.deleteByFile(fileToDelete);
        fileMetadataRepository.delete(fileToDelete);

        applicationEventPublisher.publishEvent(new FileDeletedEvent(this, fileToDelete));
    }

    @Override
    @Transactional(readOnly = true)
    public String getFileName(ObjectId fileId) throws FileNotFoundException {
        return fileMetadataRepository.findById(fileId)
                .map(metadata -> metadata.getName() + "." + metadata.getContentType().getExtension())
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }

    @Override
    @Transactional
    @EventListener
    @Order(1)
    public void processVersionCreatedEvent(VersionCreatedEvent event) {
        Version createdVersion = event.getCreatedVersion();
        FileMetadata modifiedFile = event.getModifiedFile();
        modifiedFile.setLatestVersion(createdVersion);
        Integer numberOfVersions = versionRepository.countByFile(modifiedFile);
        modifiedFile.setNumberOfVersions(numberOfVersions);
        fileMetadataRepository.save(modifiedFile);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentTypeDTO getContentType(ObjectId fileId) throws FileNotFoundException {
        return fileMetadataRepository.findById(fileId)
                .map(FileMetadata::getContentType)
                .map(contentType -> new ContentTypeDTO(
                        "." + contentType.getExtension(),
                        contentType.getName()
                ))
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }
}
