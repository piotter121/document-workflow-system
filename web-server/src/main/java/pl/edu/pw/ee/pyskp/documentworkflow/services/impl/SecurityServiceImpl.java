package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
        String currentUserEmail = userService.getCurrentUserEmail();
        return userProjectRepository.findUserProjectByUserEmailAndProjectId(currentUserEmail, projectId)
                .isPresent();
    }


    @Override
    public boolean isTaskParticipant(final UUID projectId, final UUID taskId) throws TaskNotFoundException {
        final String currentUserEmail = userService.getCurrentUserEmail();
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return task.getAdministrator().getEmail().equals(currentUserEmail)
                || task.getParticipants().stream()
                .map(UserSummary::getEmail)
                .anyMatch(participant -> participant.equals(currentUserEmail));
    }

    @Override
    public boolean hasAccessToTask(final UUID projectId, final UUID taskId) throws TaskNotFoundException {
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        String currentUserEmail = userService.getCurrentUserEmail();
        boolean isAdministrator = task.getAdministrator().getEmail().equals(currentUserEmail);
        boolean isParticipant = task.getParticipants().stream().map(UserSummary::getEmail)
                .anyMatch(currentUserEmail::equals);
        return isAdministrator || isParticipant
                || projectRepository.findOneById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId))
                .getAdministrator().getEmail().equals(currentUserEmail);
    }

    @Override
    public boolean isCurrentUserProjectAdministrator(final UUID projectId) {
        Project project = projectRepository.findOneById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        String currentUserEmail = userService.getCurrentUserEmail();
        return project.getAdministrator().getEmail().equals(currentUserEmail);
    }

    @Override
    public boolean isTaskAdministrator(final UUID projectId, final UUID taskId) throws TaskNotFoundException {
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        String currentUserLogin = userService.getCurrentUserEmail();
        return task.getAdministrator().getEmail().equals(currentUserLogin);
    }
}
