package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 31.12.16.
 */
public interface TaskService {
    static TaskInfoDTO mapToTaskInfoDto(Task task) {
        TaskInfoDTO dto = new TaskInfoDTO();
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setAdministrator(UserService.mapToUserInfoDTO(task.getAdministrator()));
        return dto;
    }

    static List<TaskInfoDTO> mapAllToTaskInfoDTO(List<Task> tasks) {
        return tasks != null
                ? tasks.stream().map(TaskService::mapToTaskInfoDto).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
