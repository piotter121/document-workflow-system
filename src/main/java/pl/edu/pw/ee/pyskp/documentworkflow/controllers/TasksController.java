package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by p.pysk on 02.01.2017.
 */
//@Controller
@RequiredArgsConstructor
@SuppressWarnings("SameReturnValue")
@RequestMapping("/projects/{projectId}/tasks")
public class TasksController {
    private static final Logger LOGGER = Logger.getLogger(TasksController.class);

    @NonNull
    private final TaskService taskService;

    @NonNull
    private final UserService userService;

    @NonNull
    private final ProjectService projectService;

    @GetMapping
    public String redirectToProject(@PathVariable Long projectId) {
        return String.format("redirect:/projects/%d", projectId);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public String getTaskInfo(@PathVariable UUID taskId,
                              @PathVariable UUID projectId,
                              Model model) {
        model.addAttribute("task", taskService.getTaskInfo(projectId, taskId));
        addCurrentUserToModel(model);
        model.addAttribute("projectId", projectId.toString());
        model.addAttribute("projectName", projectService.getProjectName(projectId));
        return "task";
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("@securityService.isCurrentUserProjectAdministrator(#projectId)")
    public String deleteTask(@PathVariable UUID taskId, @PathVariable UUID projectId) {
        LOGGER.debug("Received HTTP DELETE request for deletion task of id=" + taskId);
        taskService.deleteTask(projectId, taskId);
        return String.format("redirect:/projects/%s?deleted", projectId.toString());
    }

    @GetMapping("/{taskId}/addParticipant")
    public String redirectToTask(@PathVariable UUID projectId, @PathVariable UUID taskId) {
        return String.format("redirect:/projects/%s/tasks/%s", projectId.toString(), taskId.toString());
    }

    @PostMapping("/{taskId}/addParticipant")
    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
    public String processAddParticipant(@PathVariable UUID taskId, @PathVariable UUID projectId,
                                        @RequestParam(name = "participantEmail") String userEmail,
                                        Model model) {
        addCurrentUserToModel(model);
        try {
            taskService.addParticipantToTask(userEmail, projectId, taskId);
        } catch (UserNotFoundException ex) {
            model.addAttribute("addParticipantErrorMessage", "Nie istnieje użytkownik z podanym adresem e-mail");
        }
        return getTaskInfo(taskId, projectId, model);
    }

    @GetMapping("/add")
    @PreAuthorize("@securityService.canAddTask(#projectId)")
    public String getNewTaskForm(@ModelAttribute NewTaskForm newTask,
                                 @PathVariable UUID projectId,
                                 Model model) {
        addCurrentUserToModel(model);
        model.addAttribute("projectId", projectId.toString());
        model.addAttribute("projectName", projectService.getProjectName(projectId));
        return "addTask";
    }

    @PostMapping("/add")
    @PreAuthorize("@securityService.canAddTask(#projectId)")
    public String processNewTaskForm(@PathVariable UUID projectId,
                                     @ModelAttribute @Valid NewTaskForm newTask,
                                     BindingResult bindingResult,
                                     Model model) throws UserNotFoundException {
        if (bindingResult.hasErrors()) {
            return getNewTaskForm(newTask, projectId, model);
        }
        UUID taskId = taskService.createTaskFromForm(newTask, projectId);
        return String.format("redirect:/projects/%s/tasks/%s", projectId.toString(), taskId.toString());
    }

    private void addCurrentUserToModel(Model model) {
        model.addAttribute("currentUser", new UserInfoDTO(userService.getCurrentUser()));
    }

}
