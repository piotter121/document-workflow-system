package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;

import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
public interface SecurityService {
    boolean canAddTask(UUID projectId);

    boolean hasAccessToProject(UUID projectId);

    boolean isTaskParticipant(UUID projectId, UUID taskId) throws TaskNotFoundException;

    boolean hasAccessToTask(UUID projectId, UUID taskId) throws TaskNotFoundException;

    boolean isCurrentUserProjectAdministrator(UUID projectId);

    boolean isTaskAdministrator(UUID projectId, UUID taskId) throws TaskNotFoundException;
}
