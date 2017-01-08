package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by p.pysk on 02.01.2017.
 */
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return Optional.ofNullable(taskRepository.findOne(id));
    }

    @Override
    public Task createTaskFromForm(NewTaskForm form, Long projectId) {
        Task task = new Task();
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setCreationDate(new Date());

        task.setProject(projectService.getOneById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId)));

        String administratorEmail = form.getAdministratorEmail();
        task.setAdministrator(userService.getUserByEmail(administratorEmail)
                .orElseThrow(() -> new UserNotFoundException(administratorEmail)));

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.delete(taskId);
    }

}
