package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by piotr on 31.12.16.
 */
public interface TaskService {
    UUID createTaskFromForm(NewTaskForm formDTO, UUID projectId) throws ResourceNotFoundException;

    void deleteTask(UUID projectId, UUID taskId) throws ProjectNotFoundException;

    List<UserInfoDTO> addParticipantToTask(String userEmail, UUID projectId, UUID taskId)
            throws ResourceNotFoundException;

    TaskInfoDTO getTaskInfo(UUID projectId, UUID taskId) throws ResourceNotFoundException;

    void updateTaskStatistic(UUID projectId, UUID taskId) throws ResourceNotFoundException;

    boolean existsByName(UUID projectId, String taskName);

    TaskSummaryDTO getTaskSummary(UUID projectId, UUID taskId) throws TaskNotFoundException;

    UserInfoDTO getTaskAdministrator(UUID projectId, UUID taskId) throws TaskNotFoundException;

    List<UserInfoDTO> removeParticipantFromTask(String email, UUID projectId, UUID taskId)
            throws ResourceNotFoundException;
}
