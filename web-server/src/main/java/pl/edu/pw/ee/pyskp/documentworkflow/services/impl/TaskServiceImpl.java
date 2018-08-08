package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskServiceImpl implements TaskService {
    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final UserService userService;

    @NonNull
    private final ProjectService projectService;

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final VersionRepository versionRepository;

    private Task getTask(UUID projectId, UUID taskId) throws TaskNotFoundException {
        return taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public UUID createTaskFromForm(NewTaskForm form, UUID projectId) throws ResourceNotFoundException {
        Task task = new Task();
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setCreationDate(new Date());
        task.setProjectId(projectId);
        String administratorEmail = form.getAdministratorEmail();
        task.setAdministrator(new UserSummary(userService.getUserByEmail(administratorEmail)));
        task = taskRepository.save(task);
        projectService.updateProjectStatisticsForItsUsers(projectId);
        return task.getTaskId();
    }

    @Override
    public void deleteTask(UUID projectId, UUID taskId) throws ProjectNotFoundException {
        List<UUID> fileIds = fileMetadataRepository.findAllByTaskId(taskId).stream()
                .map(FileMetadata::getFileId).collect(Collectors.toList());
        versionRepository.deleteAllByFileIdIn(fileIds);
        fileMetadataRepository.deleteAllByTaskId(taskId);
        taskRepository.deleteTaskByProjectIdAndTaskId(projectId, taskId);
        projectService.updateProjectStatisticsForItsUsers(projectId);
    }

    @Override
    public List<UserInfoDTO> addParticipantToTask(String userEmail, UUID projectId, UUID taskId)
            throws ResourceNotFoundException {
        User user = userService.getUserByEmail(userEmail);
        Task task = getTask(projectId, taskId);
        UserSummary newParticipant = new UserSummary(user);
        Set<UserSummary> currentParticipants = new HashSet<>(task.getParticipants());
        boolean added = currentParticipants.add(newParticipant);
        task.setParticipants(currentParticipants);
        task = taskRepository.save(task);
        if (added) {
            projectService.updateProjectStatisticsForItsUsers(projectId);
        }
        return task.getParticipants().stream()
                .map(UserInfoDTO::new)
                .sorted(Comparator.comparing(UserInfoDTO::getFullName))
                .collect(Collectors.toList());
    }

    @Override
    public TaskInfoDTO getTaskInfo(UUID projectId, UUID taskId) throws ResourceNotFoundException {
        Task task = getTask(projectId, taskId);
        Project project = projectRepository.findOneById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        List<FileMetadata> files = fileMetadataRepository.findAllByTaskId(taskId);
        TaskInfoDTO taskInfo = new TaskInfoDTO(task, files);
        taskInfo.setProjectName(project.getName());
        taskInfo.setProjectAdministrator(new UserInfoDTO(project.getAdministrator()));
        return taskInfo;
    }

    @Override
    public void updateTaskStatistic(UUID projectId, UUID taskId) throws ResourceNotFoundException {
        List<FileMetadata> files = fileMetadataRepository.findAllByTaskId(taskId);
        Task task = getTask(projectId, taskId);
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
    public boolean existsByName(UUID projectId, String taskName) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        return tasks.stream()
                .map(Task::getName)
                .anyMatch(taskName::equals);
    }

    @Override
    public TaskSummaryDTO getTaskSummary(UUID projectId, UUID taskId) throws TaskNotFoundException {
        return taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .map(TaskSummaryDTO::new)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public UserInfoDTO getTaskAdministrator(UUID projectId, UUID taskId) throws TaskNotFoundException {
        return taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .map(task -> new UserInfoDTO(task.getAdministrator()))
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public List<UserInfoDTO> removeParticipantFromTask(String email, UUID projectId, UUID taskId)
            throws ResourceNotFoundException {
        User user = userService.getUserByEmail(email);
        Task task = getTask(projectId, taskId);
        UserSummary newParticipant = new UserSummary(user);
        Set<UserSummary> currentParticipants = task.getParticipants();
        boolean removed = currentParticipants.remove(newParticipant);
        task.setParticipants(currentParticipants);
        task = taskRepository.save(task);
        if (removed) {
            projectService.updateProjectStatisticsForItsUsers(projectId);
        }
        return task.getParticipants().stream()
                .map(UserInfoDTO::new)
                .sorted(Comparator.comparing(UserInfoDTO::getFullName))
                .collect(Collectors.toList());
    }

}
