package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;

import java.util.UUID;

/**
 * Created by piotr on 31.12.16.
 */
public interface TaskService {
    Task getTask(UUID projectId, UUID taskId);

    UUID createTaskFromForm(NewTaskForm formDTO, UUID projectId);

    void deleteTask(UUID projectId, UUID taskId);

    void addParticipantToTask(String userEmail, UUID projectId, UUID taskId);

    TaskInfoDTO getTaskInfo(UUID projectId, UUID taskId);

    void updateTaskStatistic(UUID projectId, UUID taskId);

    String getTaskName(UUID projectId, UUID taskId);
}
