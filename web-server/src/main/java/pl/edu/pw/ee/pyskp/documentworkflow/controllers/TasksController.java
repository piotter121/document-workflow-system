package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * Created by p.pysk on 02.01.2017.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/api/projects/{projectId}/tasks")
public class TasksController {
    @NonNull
    private final TaskService taskService;

    @GetMapping("/exists")
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public boolean existsByName(@PathVariable UUID projectId, @RequestParam String taskName) {
        return taskService.existsByName(projectId, taskName);
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
            throws ResourceNotFoundException {
        UUID taskId = taskService.createTaskFromForm(newTask, projectId);
        return Collections.singletonMap("taskId", taskId.toString());
    }

}
