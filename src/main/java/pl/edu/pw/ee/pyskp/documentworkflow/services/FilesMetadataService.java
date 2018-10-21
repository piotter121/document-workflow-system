package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.validation.annotation.Validated;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.ContentTypeDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnsupportedContentType;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by p.pysk on 04.01.2017.
 */
@Validated
public interface FilesMetadataService extends ApplicationEventPublisherAware {
    UUID createNewFileFromForm(@Valid NewFileForm formData, UUID projectId, UUID taskId)
            throws UnsupportedContentType, ResourceNotFoundException;

    FileMetadataDTO getFileMetadataDTO(UUID taskId, UUID fileId) throws FileNotFoundException;

    FileMetadata getFileMetadata(UUID taskId, UUID fileId) throws FileNotFoundException;

    void markFileToConfirm(UUID taskId, UUID fileId);

    boolean hasContentTypeAs(UUID taskId, UUID fileId, byte[] file) throws FileNotFoundException;

    void confirmFile(UUID taskId, UUID fileId);

    void deleteFile(UUID projectId, UUID taskId, UUID fileId);

    String getFileName(UUID taskId, UUID fileId) throws FileNotFoundException;

    ContentTypeDTO getContentType(UUID taskId, UUID fileId) throws FileNotFoundException;
}
