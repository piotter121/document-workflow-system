package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;

/**
 * Created by piotr on 06.01.17.
 */
public interface SecurityService {
    boolean canAddTask(Long projectId) throws ProjectNotFoundException;

    boolean hasAccessToProject(Long projectId);

    boolean isTaskParticipant(Long taskId) throws TaskNotFoundException;

    boolean hasAccessToTask(Long taskId) throws TaskNotFoundException;

    boolean isCurrentUserProjectAdministrator(Long projectId) throws ProjectNotFoundException;

    boolean isTaskAdministrator(Long taskId) throws TaskNotFoundException;
}
