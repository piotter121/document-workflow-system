package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserSummary;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.SecurityService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
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
    private final TaskService taskService;

    @Override
    public boolean canAddTask(UUID projectId) throws ProjectNotFoundException {
        return isCurrentUserProjectAdministrator(projectId);
    }

    @Override
    public boolean hasAccessToProject(UUID projectId) {
        String currentUserEmail = userService.getCurrentUserEmail();
        return userProjectRepository.findByUserEmailAndProjectId(currentUserEmail, projectId)
                .isPresent();
    }


    @Override
    public boolean isTaskParticipant(final UUID projectId, final UUID taskId) throws ResourceNotFoundException {
        Task task = taskService.getTask(projectId, taskId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        return hasCurrentUserAccessToTask(project, task);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasCurrentUserAccessToTask(Project project, Task task) {
        String currentUserEmail = userService.getCurrentUserEmail();
        return project.getAdministrator().getEmail().equals(currentUserEmail)
                || task.getAdministrator().getEmail().equals(currentUserEmail)
                || task.getParticipants()
                .stream()
                .map(UserSummary::getEmail)
                .anyMatch(participant -> participant.equals(currentUserEmail));
    }

    @Override
    public boolean hasAccessToTask(final UUID projectId, final UUID taskId) throws ResourceNotFoundException {
        return isTaskParticipant(projectId, taskId);
    }

    @Override
    public boolean isCurrentUserProjectAdministrator(final UUID projectId) throws ProjectNotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        String currentUserEmail = userService.getCurrentUserEmail();
        return project.getAdministrator().getEmail().equals(currentUserEmail);
    }

    @Override
    public boolean isTaskAdministrator(final UUID projectId, final UUID taskId) throws TaskNotFoundException {
        Task task = taskService.getTask(projectId, taskId);
        String currentUserLogin = userService.getCurrentUserEmail();
        return task.getAdministrator().getEmail().equals(currentUserLogin);
    }
}
