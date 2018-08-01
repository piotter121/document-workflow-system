package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;

import java.util.UUID;

/**
 * Created by piotr on 31.12.16.
 */
public interface TaskService {
    Task getTask(UUID projectId, UUID taskId) throws TaskNotFoundException;

    UUID createTaskFromForm(NewTaskForm formDTO, UUID projectId) throws UserNotFoundException;

    void deleteTask(UUID projectId, UUID taskId);

    void addParticipantToTask(String userEmail, UUID projectId, UUID taskId) throws UserNotFoundException, TaskNotFoundException;

    TaskInfoDTO getTaskInfo(UUID projectId, UUID taskId) throws TaskNotFoundException;

    void updateTaskStatistic(UUID projectId, UUID taskId) throws TaskNotFoundException;

    String getTaskName(UUID projectId, UUID taskId) throws TaskNotFoundException;

    boolean existsByName(UUID projectId, String taskName);
}
