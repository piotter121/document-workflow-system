package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by p.pysk on 02.01.2017.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/api/projects/{projectId}/tasks")
public class TasksController {
    private final Logger logger = Logger.getLogger(TasksController.class);

    @NonNull
    private final TaskService taskService;

    @NonNull
    private final UserService userService;

    @NonNull
    private final ProjectService projectService;

    @GetMapping("/{taskId}")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public TaskInfoDTO getTaskInfo(@PathVariable UUID taskId, @PathVariable UUID projectId)
            throws TaskNotFoundException {
        return taskService.getTaskInfo(projectId, taskId);
    }

    @GetMapping("/exists")
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public boolean existsByName(@PathVariable UUID projectId, @RequestParam String taskName) {
        return taskService.existsByName(projectId, taskName);
    }


    @DeleteMapping("/{taskId}")
    @PreAuthorize("@securityService.isCurrentUserProjectAdministrator(#projectId)")
    public void deleteTask(@PathVariable UUID taskId, @PathVariable UUID projectId) {
        if (logger.isDebugEnabled())
            logger.debug("Received HTTP DELETE request for deletion task of id=" + taskId);
        taskService.deleteTask(projectId, taskId);
    }

    //
//    @GetMapping("/{taskId}/addParticipant")
//    public String redirectToTask(@PathVariable UUID projectId, @PathVariable UUID taskId) {
//        return String.format("redirect:/projects/%s/tasks/%s", projectId.toString(), taskId.toString());
//    }
//
//    @PostMapping("/{taskId}/addParticipant")
//    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
//    public String processAddParticipant(@PathVariable UUID taskId, @PathVariable UUID projectId,
//                                        @RequestParam(name = "participantEmail") String userEmail,
//                                        Model model) {
//        addCurrentUserToModel(model);
//        try {
//            taskService.addParticipantToTask(userEmail, projectId, taskId);
//        } catch (UserNotFoundException ex) {
//            model.addAttribute("addParticipantErrorMessage", "Nie istnieje użytkownik z podanym adresem e-mail");
//        }
//        return getTaskInfo(taskId, projectId, model);
//    }
//
//    @GetMapping("/add")
//    @PreAuthorize("@securityService.canAddTask(#projectId)")
//    public String getNewTaskForm(@ModelAttribute NewTaskForm newTask,
//                                 @PathVariable UUID projectId,
//                                 Model model) {
//        addCurrentUserToModel(model);
//        model.addAttribute("projectId", projectId.toString());
//        model.addAttribute("projectName", projectService.getProjectName(projectId));
//        return "addTask";
//    }
//
    @PostMapping
    @PreAuthorize("@securityService.canAddTask(#projectId)")
    public Map<String, String> processNewTaskForm(@PathVariable UUID projectId,
                                                  @RequestBody @Valid NewTaskForm newTask)
            throws UserNotFoundException {
        UUID taskId = taskService.createTaskFromForm(newTask, projectId);
        return Collections.singletonMap("taskId", taskId.toString());
    }

    @GetMapping("/{taskId}/name")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public Map<String, String> getProjectAndTaskName(@PathVariable UUID projectId, @PathVariable UUID taskId)
            throws TaskNotFoundException {
        Map<String, String> result = new HashMap<>(2);
        result.put("projectName", projectService.getProjectName(projectId));
        result.put("taskName", taskService.getTaskName(projectId, taskId));
        return result;
    }

}
