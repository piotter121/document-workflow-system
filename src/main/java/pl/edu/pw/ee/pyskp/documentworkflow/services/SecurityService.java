package pl.edu.pw.ee.pyskp.documentworkflow.services;

import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
public interface SecurityService {
    boolean canAddTask(UUID projectId);

    boolean hasAccessToProject(UUID projectId);

    boolean isTaskParticipant(UUID projectId, UUID taskId);

    boolean hasAccessToTask(UUID projectId, UUID taskId);

    boolean isCurrentUserProjectAdministrator(UUID projectId);

    boolean isTaskAdministrator(UUID projectId, UUID taskId);
}
