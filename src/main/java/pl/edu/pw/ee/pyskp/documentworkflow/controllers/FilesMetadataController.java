package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by piotr on 04.01.17.
 */
@Controller
@RequestMapping("/projects/{projectId}/tasks/{taskId}/files")
public class FilesMetadataController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private FilesMetadataService filesMetadataService;

    @GetMapping
    public String redirectToTask(@PathVariable UUID projectId, @PathVariable UUID taskId) {
        return String.format("redirect:/projects/%s/tasks/%s", projectId, taskId);
    }

    @GetMapping("/{fileId}")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public String getFileInfo(@PathVariable UUID projectId, @PathVariable UUID fileId,
                              @PathVariable UUID taskId, Model model) {
        addCurrentUserToModel(model);
        Task task = taskService.getTask(projectId, taskId);
        model.addAttribute("task", new TaskSummaryDTO(task));
        model.addAttribute("file", filesMetadataService.getFileMetadataDTO(taskId, fileId));
        model.addAttribute("projectId", projectId.toString());
        model.addAttribute("taskAdministrator", new UserInfoDTO(task.getAdministrator()));
        return "file";
    }

    @DeleteMapping("/{fileId}")
    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
    public String deleteFile(@PathVariable UUID fileId, @PathVariable UUID taskId, @PathVariable UUID projectId) {
        filesMetadataService.deleteFile(taskId, fileId);
        return String.format("redirect:/projects/%s/tasks/%s", projectId.toString(), taskId.toString());
    }

    @GetMapping("/add")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public String getNewFileForm(Model model, @PathVariable UUID taskId,
                                 @PathVariable UUID projectId,
                                 @ModelAttribute NewFileForm formData) {
        model.addAttribute("taskId", taskId.toString());
        model.addAttribute("projectId", projectId.toString());
        model.addAttribute("taskName", taskService.getTaskName(projectId, taskId));
        addCurrentUserToModel(model);
        return "addFile";
    }

    private void addCurrentUserToModel(Model model) {
        model.addAttribute("currentUser", new UserInfoDTO(userService.getCurrentUser()));
    }

    @PostMapping("/add")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public String processNewFileForm(@RequestParam MultipartFile file,
                                     @ModelAttribute @Valid NewFileForm formData,
                                     @PathVariable UUID taskId,
                                     @PathVariable UUID projectId,
                                     BindingResult bindingResult,
                                     Model model) throws IOException {
        if (file == null || file.isEmpty()) {
            bindingResult.rejectValue("file", "NotNull");
        }
        if (bindingResult.hasErrors()) {
            addCurrentUserToModel(model);
            return "addFile";
        }
        formData.setFile(file);
        UUID fileId = filesMetadataService.createNewFileFromForm(formData, projectId, taskId);

        return String.format("redirect:/projects/%s/tasks/%s/files/%s",
                projectId.toString(), taskId.toString(), fileId.toString());
    }

    @PutMapping("/{fileId}/markToConfirm")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId, #fileId)")
    public String markFileToConfirm(@PathVariable UUID fileId,
                                    @PathVariable UUID projectId,
                                    @PathVariable UUID taskId) {
        filesMetadataService.markFileToConfirm(taskId, fileId);
        return String.format("redirect:/projects/%s/tasks/%s/files/%s",
                projectId.toString(), taskId.toString(), fileId.toString());
    }

    @PostMapping("/{fileId}/confirm")
    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
    public String confirmFile(@PathVariable UUID fileId,
                              @PathVariable UUID projectId,
                              @PathVariable UUID taskId) {
        filesMetadataService.confirmFile(taskId, fileId);
        return String.format("redirect:/projects/%s/tasks/%s/files/%s",
                projectId.toString(), taskId.toString(), fileId.toString());
    }
}
