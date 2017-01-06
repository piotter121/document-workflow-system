package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.*;

/**
 * Created by piotr on 06.01.17.
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
    private final UserService userService;
    private final TaskService taskService;
    private final ProjectService projectService;
    private final FilesMetadataService filesMetadataService;

    public SecurityServiceImpl(UserService userService,
                               TaskService taskService,
                               ProjectService projectService,
                               FilesMetadataService filesMetadataService) {
        this.userService = userService;
        this.taskService = taskService;
        this.projectService = projectService;
        this.filesMetadataService = filesMetadataService;
    }

    @Override
    public boolean canAddTask(long projectId) {
        return userService.getCurrentUser().equals(
                projectService.getOneById(projectId)
                .map(Project::getAdministrator)
                .orElseThrow(() -> new ProjectNotFoundException(projectId)));
    }

    @Override
    public boolean canDeleteTask(long taskId) {
        return taskService.getTaskById(taskId)
                .map(Task::getProject)
                .orElseThrow(() -> new TaskNotFoundException(taskId))
                .getAdministrator().equals(userService.getCurrentUser());
    }

    @Override
    public boolean hasAccessToProject(long projectId) {
        User currentUser = userService.getCurrentUser();
        return projectService.getOneById(projectId)
                .map(currentUser::hasAccessToProject)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    @Override
    public boolean hasAccessToTask(long taskId) {
        User currentUser = userService.getCurrentUser();
        return taskService.getTaskById(taskId)
                .map(currentUser::hasAccessToTask)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public boolean hasAccessToFile(long fileId) {
        return userService.getCurrentUser().hasAccessToTask(
                filesMetadataService.getOneById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId))
                .getTask());
    }
}
