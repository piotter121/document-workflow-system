package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by p.pysk on 02.01.2017.
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final UserService userService;

    @NonNull
    private final ProjectService projectService;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final UserProjectRepository userProjectRepository;

    @Override
    public Task getTask(UUID projectId, UUID taskId) {
        return taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public UUID createTaskFromForm(NewTaskForm form, UUID projectId) throws UserNotFoundException {
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
        taskRepository.deleteTaskByProjectIdAndTaskId(projectId, taskId);
        projectService.updateProjectStatisticsForItsUsers(projectId);
    }

    @Override
    public void addParticipantToTask(String userEmail, UUID projectId, UUID taskId) throws UserNotFoundException {
        User user = userService.getUserByEmail(userEmail);
        Task task = taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        UserSummary newParticipant = new UserSummary(user);
        Set<UserSummary> currentParticipants = new HashSet<>(task.getParticipants());
        boolean added = currentParticipants.add(newParticipant);
        task.setParticipants(currentParticipants);
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
