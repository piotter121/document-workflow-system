package pl.edu.pw.ee.pyskp.documentworkflow.service;

/**
 * Created by piotr on 06.01.17.
 */
public interface SecurityService {
    boolean canAddTask(long projectId);

    boolean canDeleteTask(long taskId);

    boolean hasAccessToProject(long projectId);

    boolean hasAccessToTask(long taskId);

    boolean hasAccessToFile(long fileId);
}
