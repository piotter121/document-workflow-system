package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.ContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UnknownContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.service.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.VersionService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 06.01.17.
 */
@Service
public class FilesMetadataServiceImpl implements FilesMetadataService {
    private final static Logger logger = Logger.getLogger(FilesMetadataServiceImpl.class);

    private final FileMetadataRepository fileMetadataRepository;
    private final VersionService versionService;

    public FilesMetadataServiceImpl(FileMetadataRepository fileMetadataRepository,
                                    VersionService versionService) {
        this.fileMetadataRepository = fileMetadataRepository;
        this.versionService = versionService;
    }

    @Override
    public FileMetadata createNewFileFromForm(NewFileForm formData, Task task) throws UnknownContentType {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setName(formData.getName());
        fileMetadata.setDescription(formData.getDescription());
        fileMetadata.setConfirmed(false);
        fileMetadata.setMarkedToConfirm(false);
        fileMetadata.setTask(task);
        try {
            fileMetadata.setContentType(getContentType(formData.getFile()));
            Version initVersion = versionService.createUnmanagedInitVersionOfFile(formData);
            initVersion.setFileMetadata(fileMetadata);
            fileMetadata.setVersions(Collections.singletonList(initVersion));
        } catch (IOException | TikaException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Exception found during creating new file", e);
        }

        return fileMetadataRepository.save(fileMetadata);
    }

    @Override
    public Optional<FileMetadata> getOneById(long id) {
        return Optional.ofNullable(fileMetadataRepository.findOne(id));
    }

    @Override
    public void markFileToConfirm(long fileId) {
        FileMetadata file = getOneById(fileId).orElseThrow(() -> new FileNotFoundException(fileId));
        file.setMarkedToConfirm(true);
        fileMetadataRepository.saveAndFlush(file);
    }

    @Override
    public boolean hasContentTypeAs(long fileId, MultipartFile file) {
        FileMetadata fileMetadata = getOneById(fileId).orElseThrow(() -> new FileNotFoundException(fileId));
        ContentType contentType;
        try {
            contentType = getContentType(file);
        } catch (IOException | UnknownContentType e) {
            logger.error(e.getLocalizedMessage(), e);
            return false;
        }
        return contentType.equals(fileMetadata.getContentType());
    }

    @Override
    public void confirmFile(long fileId) {
        FileMetadata fileToConfirm = getOneById(fileId).orElseThrow(() -> new FileNotFoundException(fileId));
        fileToConfirm.setConfirmed(true);
        fileMetadataRepository.saveAndFlush(fileToConfirm);
    }

    @Override
    public void deleteFile(long fileId) {
        fileMetadataRepository.delete(fileId);
    }

    @Override
    public boolean isValidVersionStringForFile(String versionString, long fileId) {
        List<Version> versions = getOneById(fileId)
                .map(FileMetadata::getVersions)
                .orElseThrow(() -> new FileNotFoundException(fileId));
        return versions.parallelStream()
                .map(Version::getVersionString)
                .noneMatch(string -> string.equalsIgnoreCase(versionString));
    }

    private static ContentType getContentType(MultipartFile multipartFile)
            throws UnknownContentType, IOException {
        Tika tika = new Tika();
        String contentType = tika.detect(multipartFile.getBytes());
        return ContentType.fromName(contentType)
                .orElseThrow(() -> new UnknownContentType(contentType));
    }
}
