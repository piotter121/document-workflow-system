package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UserNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by piotr on 31.12.16.
 */
public interface TaskService {
    Optional<Task> getTaskById(Long id);

    Task createTaskFromForm(NewTaskForm formDTO, Long projectId);

    void deleteTask(Long taskId);

    static TaskInfoDTO mapToTaskInfoDto(Task task) {
        TaskInfoDTO dto = new TaskInfoDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setAdministrator(UserService.mapToUserInfoDTO(task.getAdministrator()));
        dto.setCreationDate(task.getCreationDate());
        task.getModificationDate().ifPresent(dto::setModificationDate);
        dto.setProjectId(task.getProject().getId());
        dto.setParticipants(UserService.mapAllToUserInfoDTO(task.getParticipants()));
        task.getLastModifiedFile()
                .map(FilesMetadataService::mapToFileMetadataDTO)
                .ifPresent(dto::setLastModifiedFile);
        dto.setFilesInfo(FilesMetadataService.mapAllToFileMetadataDTO(task.getFiles()));
        return dto;
    }

    static List<TaskInfoDTO> mapAllToTaskInfoDTO(List<Task> tasks) {
        return tasks != null
                ? tasks.stream().map(TaskService::mapToTaskInfoDto).collect(Collectors.toList())
                : Collections.emptyList();
    }

    void addParticipantToTask(String userEmail, long taskId);
}
