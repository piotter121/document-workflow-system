package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.springframework.context.ApplicationEventPublisherAware;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.events.FileChangedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by piotr on 31.12.16.
 */
public interface TaskService extends ApplicationEventPublisherAware {
    int PRECEDENCE_ORDER = 2;

    UUID createTask(NewTaskForm formDTO, UUID projectId) throws ResourceNotFoundException;

    void deleteTask(UUID projectId, UUID taskId);

    List<UserInfoDTO> addParticipantToTask(String userEmail, UUID projectId, UUID taskId)
            throws ResourceNotFoundException;

    TaskInfoDTO getTaskInfo(UUID projectId, UUID taskId) throws ResourceNotFoundException;

    @SuppressWarnings("unused")
    void onFileChangedEvent(FileChangedEvent event) throws ResourceNotFoundException;

    boolean existsByName(UUID projectId, String taskName);

    UserInfoDTO getTaskAdministrator(UUID projectId, UUID taskId) throws TaskNotFoundException;

    List<UserInfoDTO> removeParticipantFromTask(String email, UUID projectId, UUID taskId)
            throws ResourceNotFoundException;

    Task getTask(UUID projectId, UUID taskId) throws TaskNotFoundException;
}
