package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.search.SearchResultEntry;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FileSearchService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}")
public class TaskOperationsController {
    @NonNull
    private final TaskService taskService;

    @NonNull
    private final FileSearchService fileSearchService;

    @GetMapping
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public TaskInfoDTO getTaskInfo(@PathVariable UUID taskId, @PathVariable UUID projectId)
            throws ResourceNotFoundException {
        return taskService.getTaskInfo(projectId, taskId);
    }

    @GetMapping("/administrator")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public UserInfoDTO getTaskAdministrator(@PathVariable UUID projectId, @PathVariable UUID taskId)
            throws TaskNotFoundException {
        return taskService.getTaskAdministrator(projectId, taskId);
    }

    @DeleteMapping
    @PreAuthorize("@securityService.isCurrentUserProjectAdministrator(#projectId)")
    public void deleteTask(@PathVariable UUID taskId, @PathVariable UUID projectId) {
        if (log.isDebugEnabled()) {
            log.debug("Received HTTP DELETE request for deletion task of id=" + taskId);
        }
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

    @GetMapping("/search")
    @PreAuthorize("@securityService.isTaskParticipant(#projectId, #taskId)")
    public List<SearchResultEntry> searchInTaskFiles(@RequestParam(name = "phrase") String searchPhrase,
                                                     @PathVariable UUID taskId, @PathVariable UUID projectId) {
        return fileSearchService.searchInTask(taskId, searchPhrase);
    }
}
