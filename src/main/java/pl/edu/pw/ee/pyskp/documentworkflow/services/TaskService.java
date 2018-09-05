package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.bson.types.ObjectId;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.FileCreatedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.FileDeletedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.VersionCreatedEvent;

import java.util.List;

/**
 * Created by piotr on 31.12.16.
 */
public interface TaskService {
    ObjectId createTaskFromForm(NewTaskForm formDTO, ObjectId projectId) throws ResourceNotFoundException;

    void deleteTask(ObjectId taskId);

    List<UserInfoDTO> addParticipantToTask(String userEmail, ObjectId projectId, ObjectId taskId)
            throws ResourceNotFoundException;

    TaskInfoDTO getTaskInfo(ObjectId projectId, ObjectId taskId) throws ResourceNotFoundException;

    boolean existsByName(ObjectId projectId, String taskName);

    UserInfoDTO getTaskAdministrator(ObjectId taskId) throws TaskNotFoundException;

    List<UserInfoDTO> removeParticipantFromTask(String email, ObjectId taskId)
            throws ResourceNotFoundException;

    @SuppressWarnings("unused")
    void processFileCreatedEvent(FileCreatedEvent event);

    @SuppressWarnings("unused")
    void processFileDeletedEvent(FileDeletedEvent event);

    @SuppressWarnings("unused")
    void processVersionCreatedEvent(VersionCreatedEvent event);
}
