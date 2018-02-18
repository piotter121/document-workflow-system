package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserSummary;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.SecurityService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
@RequiredArgsConstructor
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
    @NonNull
    private final UserService userService;

    @NonNull
    private final UserProjectRepository userProjectRepository;

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final TaskRepository taskRepository;

    @Override
    public boolean canAddTask(UUID projectId) {
        return isCurrentUserProjectAdministrator(projectId);
    }

    @Override
    public boolean hasAccessToProject(UUID projectId) {
        String currentUserLogin = userService.getCurrentUserLogin();
        return userProjectRepository.findUserProjectByUserLoginAndProjectId(currentUserLogin, projectId)
                .isPresent();
    }


    @Override
    public boolean isTaskParticipant(UUID projectId, UUID taskId) {
        String currentUserLogin = userService.getCurrentUserLogin();
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return task.getAdministrator().getLogin().equals(currentUserLogin)
                || task.getParticipants().stream()
                .map(UserSummary::getLogin)
                .anyMatch(participant -> participant.equals(currentUserLogin));
    }

    @Override
    public boolean hasAccessToTask(UUID projectId, UUID taskId) {
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        String currentUserLogin = userService.getCurrentUserLogin();
        boolean isAdministrator = task.getAdministrator().getLogin().equals(currentUserLogin);
        boolean isParticipant = task.getParticipants().stream().map(UserSummary::getLogin)
                .anyMatch(currentUserLogin::equals);
        return isAdministrator || isParticipant
                || projectRepository.findOneById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId))
                .getAdministrator().getLogin().equals(currentUserLogin);
    }

    @Override
    public boolean isCurrentUserProjectAdministrator(UUID projectId) {
        Project project = projectRepository.findOneById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        String currentUserLogin = userService.getCurrentUserLogin();
        return project.getAdministrator().getLogin().equals(currentUserLogin);
    }

    @Override
    public boolean isTaskAdministrator(UUID projectId, UUID taskId) {
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        String currentUserLogin = userService.getCurrentUserLogin();
        return task.getAdministrator().getLogin().equals(currentUserLogin);
    }
}
