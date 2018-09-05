package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.bson.types.ObjectId;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;

/**
 * Created by piotr on 06.01.17.
 */
public interface SecurityService {
    boolean canAddTask(ObjectId projectId) throws ProjectNotFoundException;

    boolean hasAccessToProject(ObjectId projectId);

    boolean isTaskParticipant(ObjectId projectId, ObjectId taskId) throws ResourceNotFoundException;

    boolean hasAccessToTask(ObjectId projectId, ObjectId taskId) throws ResourceNotFoundException;

    boolean isCurrentUserProjectAdministrator(ObjectId projectId) throws ProjectNotFoundException;

    boolean isTaskAdministrator(ObjectId taskId) throws TaskNotFoundException;
}
