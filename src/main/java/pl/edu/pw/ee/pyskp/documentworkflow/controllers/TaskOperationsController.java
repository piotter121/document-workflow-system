package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}")
public class TaskOperationsController {
    @NonNull
    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("@securityService.isTaskParticipant(#taskId)")
    public TaskInfoDTO getTaskInfo(@PathVariable Long taskId) throws ResourceNotFoundException {
        return taskService.getTaskInfo(taskId);
    }

    @GetMapping("/administrator")
    @PreAuthorize("@securityService.isTaskParticipant(#taskId)")
    public UserInfoDTO getTaskAdministrator(@PathVariable Long taskId) throws TaskNotFoundException {
        return taskService.getTaskAdministrator(taskId);
    }

    @DeleteMapping
    @PreAuthorize("@securityService.isCurrentUserProjectAdministrator(#projectId)")
    public void deleteTask(@PathVariable Long taskId, @PathVariable Long projectId) {
        if (log.isDebugEnabled()) {
            log.debug("Received HTTP DELETE request for deletion task of id=" + taskId);
        }
        taskService.deleteTask(taskId);
    }

    @PutMapping("/participants")
    @PreAuthorize("@securityService.isTaskAdministrator(#taskId)")
    public List<UserInfoDTO> addParticipantsToTask(@RequestBody String email, @PathVariable Long taskId)
            throws ResourceNotFoundException {
        return taskService.addParticipantToTask(email, taskId);
    }

    @DeleteMapping("/participants")
    @PreAuthorize("@securityService.isTaskAdministrator(#taskId)")
    public List<UserInfoDTO> removeParticipantFromTask(@RequestParam String email, @PathVariable Long taskId)
            throws ResourceNotFoundException {
        return taskService.removeParticipantFromTask(email, taskId);
    }
}
