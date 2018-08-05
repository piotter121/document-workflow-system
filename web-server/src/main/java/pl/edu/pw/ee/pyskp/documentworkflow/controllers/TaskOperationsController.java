package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}")
public class TaskOperationsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskOperationsController.class);

    @NonNull
    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public TaskInfoDTO getTaskInfo(@PathVariable UUID taskId, @PathVariable UUID projectId)
            throws ResourceNotFoundException {
        return taskService.getTaskInfo(projectId, taskId);
    }

    @GetMapping("/summary")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public TaskSummaryDTO getTaskSummary(@PathVariable UUID projectId, @PathVariable UUID taskId)
            throws TaskNotFoundException {
        return taskService.getTaskSummary(projectId, taskId);
    }

    @GetMapping("/administrator")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public UserInfoDTO getTaskAdministrator(@PathVariable UUID projectId, @PathVariable UUID taskId)
            throws TaskNotFoundException {
        return taskService.getTaskAdministrator(projectId, taskId);
    }

    @DeleteMapping
    @PreAuthorize("@securityService.isCurrentUserProjectAdministrator(#projectId)")
    public void deleteTask(@PathVariable UUID taskId, @PathVariable UUID projectId) throws ProjectNotFoundException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Received HTTP DELETE request for deletion task of id=" + taskId);
        taskService.deleteTask(projectId, taskId);
    }

    @PutMapping("/participants")
    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
    public List<UserInfoDTO> addParticipantsToTask(@RequestBody String email, @PathVariable UUID projectId,
                                                   @PathVariable UUID taskId) throws ResourceNotFoundException {
        return taskService.addParticipantToTask(email, projectId, taskId);
    }

    @DeleteMapping("/participants")
    @PreAuthorize("@securityService.isTaskAdministrator(#projectId, #taskId)")
    public List<UserInfoDTO> removeParticipantFromTask(@RequestParam String email, @PathVariable UUID projectId,
                                                       @PathVariable UUID taskId) throws ResourceNotFoundException {
        return taskService.removeParticipantFromTask(email, projectId, taskId);
    }
}
