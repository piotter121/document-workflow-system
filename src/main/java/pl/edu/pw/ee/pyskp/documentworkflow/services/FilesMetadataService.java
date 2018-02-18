package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnknownContentType;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by p.pysk on 04.01.2017.
 */
public interface FilesMetadataService {
    UUID createNewFileFromForm(NewFileForm formData, UUID projectId, UUID taskId)
            throws UnknownContentType, IOException;

    FileMetadataDTO getFileMetadataDTO(UUID taskId, UUID fileId);

    void markFileToConfirm(UUID taskId, UUID fileId);

    boolean hasContentTypeAs(UUID taskId, UUID fileId, MultipartFile file);

    void confirmFile(UUID taskId, UUID fileId);

    @Transactional(rollbackFor = Throwable.class)
    void deleteFile(UUID projectId, UUID taskId, UUID fileId);

    boolean isValidVersionStringForFile(String versionString, UUID fileId);

    String getFileName(UUID taskId, UUID fileId);
}
