package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import javax.validation.Valid;

/**
 * Created by piotr on 04.01.17.
 */
@Controller
@RequestMapping("/projects/{projectId}/tasks/{taskId}/files")
public class FilesMetadataController {
    private final TaskService taskService;
    private final UserService userService;
    private final FilesMetadataService filesMetadataService;

    public FilesMetadataController(TaskService taskService, UserService userService,
                                   FilesMetadataService filesMetadataService) {
        this.taskService = taskService;
        this.userService = userService;
        this.filesMetadataService = filesMetadataService;
    }

    @GetMapping
    public String redirectToTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        return String.format("redirect:/projects/%d/tasks/%d", projectId, taskId);
    }

    @GetMapping("/{fileId}")
    public String getFileInfo(@PathVariable long fileId, @PathVariable long taskId, Model model) {
        addCurrentUserToModel(model);
        model.addAttribute("task", taskService.getTaskById(taskId)
                .map(TaskService::mapToTaskInfoDto)
                .orElseThrow(() -> new TaskNotFoundException(taskId)));
        model.addAttribute("file", filesMetadataService.getOneById(fileId)
                .map(FilesMetadataService::mapToFileMetadataDTO)
                .orElseThrow(() -> new FileNotFoundException(fileId)));
        return "file";
    }

    @GetMapping("/add")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public String getNewFileForm(Model model, @PathVariable long taskId,
                                 @ModelAttribute NewFileForm formData) {
        TaskInfoDTO taskInfoDTO = taskService.getTaskById(taskId)
                .map(TaskService::mapToTaskInfoDto)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        model.addAttribute("task", taskInfoDTO);
        addCurrentUserToModel(model);
        return "addFile";
    }

    private void addCurrentUserToModel(Model model) {
        model.addAttribute("currentUser", UserService.mapToUserInfoDTO(userService.getCurrentUser()));
    }

    @PostMapping("/add")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public String processNewFileForm(@RequestParam MultipartFile file,
                                     @ModelAttribute @Valid NewFileForm formData,
                                     @PathVariable long taskId,
                                     @PathVariable long projectId,
                                     BindingResult bindingResult,
                                     Model model) {
        if (file == null || file.isEmpty()) {
            bindingResult.rejectValue("file", "NotNull");
        }
        if (bindingResult.hasErrors()) {
            addCurrentUserToModel(model);
            return "addFile";
        }
        formData.setFile(file);
        Task task = taskService.getTaskById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        long fileId = filesMetadataService.createNewFileFromForm(formData, task).getId();

        return String.format("redirect:/projects/%d/tasks/%d/files/%d",
                projectId, taskId, fileId);
    }
}
