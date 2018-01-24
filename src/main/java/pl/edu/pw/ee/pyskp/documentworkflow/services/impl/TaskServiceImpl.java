package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by p.pysk on 02.01.2017.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Override
    public Task getTask(UUID projectId, UUID taskId) {
        return taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public UUID createTaskFromForm(NewTaskForm form, UUID projectId) {
        Task task = new Task();
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setCreationDate(new Date());
        task.setProjectId(projectId);
        String administratorEmail = form.getAdministratorEmail();
        task.setAdministrator(new UserSummary(userService.getUserByEmail(administratorEmail)));
        task.setProjectName(projectService.getProjectName(projectId));
        task = taskRepository.save(task);
        projectService.updateProjectStatisticsForItsUsers(projectId);
        return task.getTaskId();
    }

    @Override
    public void deleteTask(UUID projectId, UUID taskId) {
        List<UUID> fileIds = fileMetadataRepository.findAllByTaskId(taskId).stream()
                .map(FileMetadata::getFileId).collect(Collectors.toList());
        versionRepository.deleteAllByFileIdIn(fileIds);
        fileMetadataRepository.deleteAllByTaskId(taskId);
        List<Task> otherTasks = taskRepository.findAllByProjectIdAndTaskIdNot(projectId, taskId);
        taskRepository.deleteTaskByProjectIdAndTaskId(projectId, taskId);
        String currentUserLogin = userService.getCurrentUserLogin();
        UserProject project
                = userProjectRepository.findUserProjectByUserLoginAndProjectId(currentUserLogin, projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        long numberOfFiles = project.getNumberOfFiles();
        project.setNumberOfFiles(numberOfFiles - fileIds.size());
        long numberOfTasks = project.getNumberOfTasks();
        project.setNumberOfTasks(numberOfTasks - 1);
        long numberOfParticipantsInProject = otherTasks.stream()
                .flatMap(task -> task.getParticipants().stream())
                .distinct()
                .count();
        project.setNumberOfParticipants(numberOfParticipantsInProject);
        FileSummary lastModifiedFile = otherTasks.stream()
                .map(Task::getLastModifiedFile)
                .max(Comparator.comparing(FileSummary::getModificationDate))
                .orElse(null);
        project.setLastModifiedFile(lastModifiedFile);
        userProjectRepository.save(project);
    }

    @Override
    public void addParticipantToTask(String userEmail, UUID projectId, UUID taskId) {
        User user = userService.getUserByEmail(userEmail);
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        boolean added = task.getParticipants().add(new UserSummary(user));
        taskRepository.save(task);
        if (added) {
            projectService.updateProjectStatisticsForItsUsers(projectId);
        }
    }

    @Override
    public TaskInfoDTO getTaskInfo(UUID projectId, UUID taskId) {
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        List<FileMetadata> files = fileMetadataRepository.findAllByTaskId(taskId);
        return new TaskInfoDTO(task, files);
    }

    @Override
    public void updateTaskStatistic(UUID projectId, UUID taskId) {
        List<FileMetadata> files = fileMetadataRepository.findAllByTaskId(taskId);
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setNumberOfFiles(files.size());
        FileSummary lastModifiedFile = files.stream()
                .max(Comparator.comparing(file -> file.getLatestVersion().getSaveDate()))
                .map(FileSummary::new)
                .orElse(null);
        task.setLastModifiedFile(lastModifiedFile);
        taskRepository.save(task);
        projectService.updateProjectStatisticsForItsUsers(projectId);
    }

    @Override
    public String getTaskName(UUID projectId, UUID taskId) {
        return taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .map(Task::getName)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

}
