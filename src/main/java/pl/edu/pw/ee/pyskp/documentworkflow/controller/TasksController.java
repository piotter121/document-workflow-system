package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.TaskService;

import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping("/{taskId}")
    public String getTaskInfo(@PathVariable Long taskId,
                              Model model) {
        Optional<Task> taskOpt = taskService.getTaskById(taskId);
        if (!taskOpt.isPresent()) throw new TaskNotFoundException(taskId);
        model.addAttribute("task", TaskService.mapToTaskInfoDto(taskOpt.get()));
        return "task";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable Long taskId, @PathVariable Long projectId) {
        logger.debug("Received HTTP DELETE request for deletion task of id=" + taskId);
        taskService.deleteTask(taskId);
        return String.format("redirect:/projects/%d?deleted", projectId);
    }

    @GetMapping("/add")
    public String getNewTaskForm(@ModelAttribute NewTaskForm newTask) {
        return "addTask";
    }

    @PostMapping("/add")
    public String processNewTaskForm(@PathVariable Long projectId,
                                     @ModelAttribute @Valid NewTaskForm newTask,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "addTask";
        Task task = taskService.createTaskFromForm(newTask, projectId);
        return String.format("redirect:/projects/%d/tasks/%d", projectId, task.getId());
    }

}
