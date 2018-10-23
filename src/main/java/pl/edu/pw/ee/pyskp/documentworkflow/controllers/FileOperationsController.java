package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.ContentTypeDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/files/{fileId}")
public class FileOperationsController {
    @NonNull
    private final FilesMetadataService filesMetadataService;

    @GetMapping
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public FileMetadataDTO getFileInfo(@PathVariable ObjectId projectId, @PathVariable ObjectId fileId,
                                       @PathVariable ObjectId taskId) throws FileNotFoundException {
        return filesMetadataService.getFileMetadataDTO(fileId);
    }

    @DeleteMapping
    @PreAuthorize("@securityService.isTaskAdministrator(#taskId)")
    public void deleteFile(@PathVariable ObjectId taskId, @PathVariable ObjectId fileId) throws FileNotFoundException {
        filesMetadataService.deleteFile(fileId);
    }

    @PostMapping("/markToConfirm")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public void markFileToConfirm(@PathVariable ObjectId fileId, @PathVariable ObjectId projectId,
                                  @PathVariable ObjectId taskId)
            throws FileNotFoundException {
        filesMetadataService.markFileToConfirm(fileId);
    }

    @PostMapping("/confirm")
    @PreAuthorize("@securityService.isTaskAdministrator(#taskId)")
    public void confirm(@PathVariable ObjectId taskId, @PathVariable ObjectId fileId) throws FileNotFoundException {
        filesMetadataService.confirmFile(fileId);
    }

    @GetMapping("/contentType")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public ContentTypeDTO getContentType(@PathVariable ObjectId projectId, @PathVariable ObjectId fileId,
                                         @PathVariable ObjectId taskId) throws FileNotFoundException {
        return filesMetadataService.getContentType(fileId);
    }
}
