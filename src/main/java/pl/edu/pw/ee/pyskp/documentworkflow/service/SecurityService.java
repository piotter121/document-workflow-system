package pl.edu.pw.ee.pyskp.documentworkflow.service;

/**
 * Created by piotr on 06.01.17.
 */
public interface SecurityService {
    boolean canAddTask(long projectId);

    boolean canAddParticipantToTask(long taskId);

    boolean canDeleteTask(long taskId);

    boolean hasAccessToProject(long projectId);

    boolean isTaskParticipant(long taskId);

    boolean hasAccessToFile(long fileId);

    boolean canDeleteProject(long projectId);

    boolean canConfirmFile(long fileId);

    boolean canDeleteFile(long fileId);
}
