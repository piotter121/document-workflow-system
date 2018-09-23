package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.SecurityService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

/**
 * Created by piotr on 06.01.17.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
    @NonNull
    private final UserService userService;

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final TaskService taskService;

    @NonNull
    private final ProjectService projectService;

    @Override
    @Transactional(readOnly = true)
    public boolean canAddTask(Long projectId) throws ProjectNotFoundException {
        return isCurrentUserProjectAdministrator(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasAccessToProject(Long projectId) {
        String currentUserEmail = userService.getCurrentUserEmail();
        return projectRepository.hasAccessToProject(currentUserEmail, projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTaskParticipant(Long taskId) throws TaskNotFoundException {
        String currentUserEmail = userService.getCurrentUserEmail();
        Task task = taskService.getTaskWithFetchedParticipants(taskId);
        Project project = task.getProject();
        return project.getAdministrator().getEmail().equals(currentUserEmail) ||
                task.getAdministrator().getEmail().equals(currentUserEmail) ||
                task.getParticipants()
                        .stream()
                        .map(User::getEmail)
                        .anyMatch(currentUserEmail::equals);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasAccessToTask(Long taskId) throws TaskNotFoundException {
        return isTaskParticipant(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCurrentUserProjectAdministrator(Long projectId) throws ProjectNotFoundException {
        Project project = projectService.getProject(projectId);
        String currentUserEmail = userService.getCurrentUserEmail();
        return project.getAdministrator().getEmail().equals(currentUserEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTaskAdministrator(Long taskId) throws TaskNotFoundException {
        Task task = taskService.getTask(taskId);
        String currentUserLogin = userService.getCurrentUserEmail();
        return task.getAdministrator().getEmail().equals(currentUserLogin);
    }
}
