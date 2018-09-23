package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;

@SuppressWarnings("MVCPathVariableInspection")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/files/{fileId}")
public class FileOperationsController {
    @NonNull
    private final FilesMetadataService filesMetadataService;

    @GetMapping
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public FileMetadataDTO getFileInfo(@PathVariable Long fileId, @PathVariable Long taskId)
            throws FileNotFoundException {
        return filesMetadataService.getFileMetadataDTO(fileId);
    }

    @DeleteMapping
    @PreAuthorize("@securityService.isTaskAdministrator(#taskId)")
    public void deleteFile(@PathVariable Long taskId, @PathVariable Long fileId) {
        filesMetadataService.deleteFile(fileId);
    }

    @PostMapping("/markToConfirm")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public void markFileToConfirm(@PathVariable Long fileId, @PathVariable Long taskId) throws FileNotFoundException {
        filesMetadataService.markFileToConfirm(fileId);
    }

    @PostMapping("/confirm")
    @PreAuthorize("@securityService.isTaskAdministrator(#taskId)")
    public void confirm(@PathVariable Long taskId, @PathVariable Long fileId)
            throws FileNotFoundException {
        filesMetadataService.confirmFile(fileId);
    }
}
