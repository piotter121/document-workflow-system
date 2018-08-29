package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;

import java.util.UUID;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/files/{fileId}")
public class FileOperationsController {
    @NonNull
    private final FilesMetadataService filesMetadataService;

    @GetMapping
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public FileMetadataDTO getFileInfo(@PathVariable UUID projectId, @PathVariable UUID fileId,
                                       @PathVariable UUID taskId) throws FileNotFoundException {
        return filesMetadataService.getFileMetadataDTO(taskId, fileId);
    }

    @DeleteMapping
    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
    public void deleteFile(@PathVariable UUID projectId, @PathVariable UUID taskId, @PathVariable UUID fileId)
            throws ResourceNotFoundException {
        filesMetadataService.deleteFile(projectId, taskId, fileId);
    }

    @PostMapping("/markToConfirm")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public void markFileToConfirm(@PathVariable UUID fileId, @PathVariable UUID projectId, @PathVariable UUID taskId)
            throws FileNotFoundException {
        filesMetadataService.markFileToConfirm(taskId, fileId);
    }

    @PostMapping("/confirm")
    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
    public void confirm(@PathVariable UUID projectId, @PathVariable UUID taskId, @PathVariable UUID fileId)
            throws FileNotFoundException {
        filesMetadataService.confirmFile(taskId, fileId);
    }
}
