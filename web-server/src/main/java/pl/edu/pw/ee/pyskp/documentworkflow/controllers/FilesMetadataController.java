package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnknownContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by piotr on 04.01.17.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/files")
public class FilesMetadataController {

    @NonNull
    private final TaskService taskService;

    @NonNull
    private final UserService userService;

    @NonNull
    private final FilesMetadataService filesMetadataService;

    //    @GetMapping("/{fileId}")
//    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
//    public String getFileInfo(@PathVariable UUID projectId, @PathVariable UUID fileId,
//                              @PathVariable UUID taskId, Model model) {
//        addCurrentUserToModel(model);
//        Task task = taskService.getTask(projectId, taskId);
//        model.addAttribute("task", new TaskSummaryDTO(task));
//        model.addAttribute("file", filesMetadataService.getFileMetadataDTO(taskId, fileId));
//        model.addAttribute("projectId", projectId.toString());
//        model.addAttribute("taskAdministrator", new UserInfoDTO(task.getAdministrator()));
//        return "file";
//    }
//
//    @DeleteMapping("/{fileId}")
//    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
//    public String deleteFile(@PathVariable UUID fileId, @PathVariable UUID taskId, @PathVariable UUID projectId) {
//        filesMetadataService.deleteFile(projectId, taskId, fileId);
//        return String.format("redirect:/projects/%s/tasks/%s", projectId.toString(), taskId.toString());
//    }
//
//    @GetMapping("/add")
//    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
//    public String getNewFileForm(Model model, @PathVariable UUID taskId,
//                                 @PathVariable UUID projectId,
//                                 @ModelAttribute NewFileForm formData) {
//        model.addAttribute("taskId", taskId.toString());
//        model.addAttribute("projectId", projectId.toString());
//        model.addAttribute("taskName", taskService.getTaskName(projectId, taskId));
//        addCurrentUserToModel(model);
//        return "addFile";
//    }
//
//    private void addCurrentUserToModel(Model model) {
//        model.addAttribute("currentUser", new UserInfoDTO(userService.getCurrentUser()));
//    }
//
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public String processNewFileForm(@RequestPart(name = "name") String name,
                                     @RequestPart(name = "description", required = false) String description,
                                     @RequestPart(name = "file") MultipartFile file,
                                     @RequestPart(name = "versionString") String versionString,
                                     @PathVariable UUID taskId,
                                     @PathVariable UUID projectId)
            throws IOException, UnknownContentType, TaskNotFoundException {
        NewFileForm newFileForm = new NewFileForm(name, description, file, versionString);
        UUID fileId = filesMetadataService.createNewFileFromForm(newFileForm, projectId, taskId);
        return fileId.toString();
    }
//
//    @PutMapping("/{fileId}/markToConfirm")
//    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
//    public String markFileToConfirm(@PathVariable UUID fileId,
//                                    @PathVariable UUID projectId,
//                                    @PathVariable UUID taskId) {
//        filesMetadataService.markFileToConfirm(taskId, fileId);
//        return String.format("redirect:/projects/%s/tasks/%s/files/%s",
//                projectId.toString(), taskId.toString(), fileId.toString());
//    }
//
//    @PostMapping("/{fileId}/confirm")
//    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
//    public String confirmFile(@PathVariable UUID fileId,
//                              @PathVariable UUID projectId,
//                              @PathVariable UUID taskId) {
//        filesMetadataService.confirmFile(taskId, fileId);
//        return String.format("redirect:/projects/%s/tasks/%s/files/%s",
//                projectId.toString(), taskId.toString(), fileId.toString());
//    }
}
