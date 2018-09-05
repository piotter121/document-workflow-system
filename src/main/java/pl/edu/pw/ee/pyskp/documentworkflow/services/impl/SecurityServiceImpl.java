package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.SecurityService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import java.util.List;

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
    private final TaskRepository taskRepository;

    @Override
    public boolean canAddTask(ObjectId projectId) throws ProjectNotFoundException {
        return isCurrentUserProjectAdministrator(projectId);
    }

    @Override
    public boolean hasAccessToProject(ObjectId projectId) {
        String currentUserEmail = userService.getCurrentUserEmail();
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            return false;
        }
        if (currentUserEmail.equals(project.getAdministrator().getEmail())) {
            return true;
        }
        List<Task> tasks = taskRepository.findByProject(project);
        if (tasks.stream()
                .flatMap(task -> task.getParticipants().stream())
                .map(User::getEmail)
                .distinct()
                .anyMatch(currentUserEmail::equals)) {
            return true;
        }
        return tasks.stream()
                .map(task -> task.getAdministrator().getEmail())
                .distinct()
                .anyMatch(currentUserEmail::equals);
    }


    @Override
    public boolean isTaskParticipant(ObjectId projectId, ObjectId taskId) throws ResourceNotFoundException {
        String currentUserEmail = userService.getCurrentUserEmail();

        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(projectId.toString());
        }
        if (currentUserEmail.equals(project.getAdministrator().getEmail())) {
            return true;
        }

        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId.toString());
        }

        if (currentUserEmail.equals(task.getAdministrator().getEmail())) {
            return true;
        }

        return task.getParticipants()
                .stream()
                .map(User::getEmail)
                .anyMatch(currentUserEmail::equals);
    }

    @Override
    public boolean hasAccessToTask(ObjectId projectId, ObjectId taskId) throws ResourceNotFoundException {
        return isTaskParticipant(projectId, taskId);
    }

    @Override
    public boolean isCurrentUserProjectAdministrator(ObjectId projectId) throws ProjectNotFoundException {
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(projectId.toString());
        }
        String currentUserEmail = userService.getCurrentUserEmail();
        return currentUserEmail.equals(project.getAdministrator().getEmail());
    }

    @Override
    public boolean isTaskAdministrator(ObjectId taskId) throws TaskNotFoundException {
        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId.toString());
        }
        String currentUserLogin = userService.getCurrentUserEmail();
        return currentUserLogin.equals(task.getAdministrator().getEmail());
    }
}
