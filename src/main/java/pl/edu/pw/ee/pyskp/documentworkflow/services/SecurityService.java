package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;

import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
public interface SecurityService {
    boolean canAddTask(UUID projectId) throws ProjectNotFoundException;

    boolean hasAccessToProject(UUID projectId);

    boolean isTaskParticipant(UUID projectId, UUID taskId) throws ResourceNotFoundException;

    boolean hasCurrentUserAccessToTask(Project project, Task task);

    boolean hasAccessToTask(UUID projectId, UUID taskId) throws ResourceNotFoundException;

    boolean isCurrentUserProjectAdministrator(UUID projectId) throws ProjectNotFoundException;

    boolean isTaskAdministrator(UUID projectId, UUID taskId) throws TaskNotFoundException;
}
