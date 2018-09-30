package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;

import java.util.List;

/**
 * Created by piotr on 31.12.16.
 */
public interface TaskService {
    Long createTaskFromForm(NewTaskForm formDTO, Long projectId) throws ResourceNotFoundException;

    void deleteTask(Long taskId);

    List<UserInfoDTO> addParticipantToTask(String userEmail, Long taskId) throws ResourceNotFoundException;

    TaskInfoDTO getTaskInfo(Long taskId) throws TaskNotFoundException;

    boolean existsByName(Long projectId, String taskName);

    UserInfoDTO getTaskAdministrator(Long taskId) throws TaskNotFoundException;

    List<UserInfoDTO> removeParticipantFromTask(String email, Long taskId) throws ResourceNotFoundException;

    TaskSummaryDTO getTaskSummary(Task task);

    int getNumberOfTasks(Project project);

    Task getTask(Long taskId) throws TaskNotFoundException;
}
