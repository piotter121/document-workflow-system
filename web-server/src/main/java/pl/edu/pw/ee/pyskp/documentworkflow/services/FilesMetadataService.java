package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.springframework.validation.annotation.Validated;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnknownContentType;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by p.pysk on 04.01.2017.
 */
@Validated
public interface FilesMetadataService {
    UUID createNewFileFromForm(@Valid NewFileForm formData, UUID projectId, UUID taskId)
            throws UnknownContentType, ResourceNotFoundException;

    FileMetadataDTO getFileMetadataDTO(UUID taskId, UUID fileId) throws FileNotFoundException;

    void markFileToConfirm(UUID taskId, UUID fileId) throws FileNotFoundException;

    boolean hasContentTypeAs(UUID taskId, UUID fileId, byte[] file) throws FileNotFoundException;

    void confirmFile(UUID taskId, UUID fileId) throws FileNotFoundException;

    void deleteFile(UUID projectId, UUID taskId, UUID fileId) throws ResourceNotFoundException;

    String getFileName(UUID taskId, UUID fileId) throws FileNotFoundException;
}
