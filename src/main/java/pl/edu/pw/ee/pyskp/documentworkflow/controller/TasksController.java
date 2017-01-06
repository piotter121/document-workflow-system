package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.TaskService;

import javax.validation.Valid;

/**
 * Created by p.pysk on 02.01.2017.
 */
@Controller
@RequestMapping("/projects/{projectId}/tasks")
public class TasksController {
    private static final Logger logger = Logger.getLogger(TasksController.class);

    private final TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String redirectToProject(@PathVariable Long projectId) {
        return String.format("redirect:/projects/%d", projectId);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public String getTaskInfo(@PathVariable Long taskId,
                              Model model) {
        TaskInfoDTO task = taskService.getTaskById(taskId)
                .map(TaskService::mapToTaskInfoDto)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        model.addAttribute("task", task);
        return "task";
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("@securityService.canDeleteTask(#taskId)")
    public String deleteTask(@PathVariable Long taskId, @PathVariable Long projectId) {
        logger.debug("Received HTTP DELETE request for deletion task of id=" + taskId);
        taskService.deleteTask(taskId);
        return String.format("redirect:/projects/%d?deleted", projectId);
    }

    @GetMapping("/add")
    @PreAuthorize("@securityService.canAddTask(#projectId)")
    public String getNewTaskForm(@ModelAttribute NewTaskForm newTask, @PathVariable long projectId) {
        return "addTask";
    }

    @PostMapping("/add")
    @PreAuthorize("@securityService.canAddTask(#projectId)")
    public String processNewTaskForm(@PathVariable long projectId,
                                     @ModelAttribute @Valid NewTaskForm newTask,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "addTask";
        long taskId = taskService.createTaskFromForm(newTask, projectId).getId();
        return String.format("redirect:/projects/%d/tasks/%d", projectId, taskId);
    }

}
