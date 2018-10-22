package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.bson.types.ObjectId;
import org.springframework.validation.annotation.Validated;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnknownContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.VersionCreatedEvent;

import javax.validation.Valid;

/**
 * Created by p.pysk on 04.01.2017.
 */
@Validated
public interface FilesMetadataService {
    ObjectId createNewFileFromForm(@Valid NewFileForm formData, ObjectId projectId, ObjectId taskId)
            throws UnknownContentType, ResourceNotFoundException;

    FileMetadataDTO getFileMetadataDTO(ObjectId fileId) throws FileNotFoundException;

    void markFileToConfirm(ObjectId fileId) throws FileNotFoundException;

    boolean hasContentTypeAs(ObjectId fileId, byte[] file) throws FileNotFoundException;

    void confirmFile(ObjectId fileId) throws FileNotFoundException;

    void deleteFile(ObjectId fileId) throws FileNotFoundException;

    String getFileName(ObjectId fileId) throws FileNotFoundException;

    @SuppressWarnings("unused")
    void processVersionCreatedEvent(VersionCreatedEvent event);
}
