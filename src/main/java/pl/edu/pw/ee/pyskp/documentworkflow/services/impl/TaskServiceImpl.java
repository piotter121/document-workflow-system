package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.events.FileChangedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.events.TaskChangedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by p.pysk on 02.01.2017.
 */
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final UserService userService;

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final VersionRepository versionRepository;

    @Setter
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional(readOnly = true)
    public Task getTask(UUID projectId, UUID taskId) throws TaskNotFoundException {
        return taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId.toString()));
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public UUID createTask(NewTaskForm form, UUID projectId) throws ResourceNotFoundException {
        Task task = new Task();
        task.setProjectId(projectId);
        task.setTaskId(UUID.randomUUID());
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setCreationDate(new Date());
        User taskAdministrator = userService.getUserByEmail(form.getAdministratorEmail());
        task.setAdministrator(new UserSummary(taskAdministrator));
        task.setNumberOfFiles(0);
        task = taskRepository.save(task);
        applicationEventPublisher.publishEvent(new TaskChangedEvent(this, projectId, task.getTaskId()));
        return task.getTaskId();
    }

    @Override
    @Transactional
    public void deleteTask(UUID projectId, UUID taskId) {
        List<UUID> fileIds = fileMetadataRepository.findByTaskId(taskId)
                .map(FileMetadata::getFileId)
                .collect(Collectors.toList());
        versionRepository.deleteByFileIdIn(fileIds);
        fileMetadataRepository.deleteAllByTaskId(taskId);
        taskRepository.deleteTaskByProjectIdAndTaskId(projectId, taskId);
        applicationEventPublisher.publishEvent(new TaskChangedEvent(this, projectId, taskId));
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public List<UserInfoDTO> addParticipantToTask(String userEmail, UUID projectId, UUID taskId)
            throws ResourceNotFoundException {
        User user = userService.getUserByEmail(userEmail);
        Task task = getTask(projectId, taskId);
        Set<UserSummary> currentParticipants = new HashSet<>(task.getParticipants());
        boolean added = currentParticipants.add(new UserSummary(user));
        return updateParticipantsAndGetDTOs(task, currentParticipants, added);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskInfoDTO getTaskInfo(UUID projectId, UUID taskId) throws ResourceNotFoundException {
        Task task = getTask(projectId, taskId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        List<FileMetadataDTO> files = fileMetadataRepository.findByTaskId(taskId)
                .map(this::getFileMetadataDTOWithEmptyVersionList)
                .collect(Collectors.toList());

        List<UserInfoDTO> participants = task.getParticipants().stream()
                .map(UserInfoDTO::fromUserSummary)
                .collect(Collectors.toList());

        FileSummaryDTO lastModifiedFileDTO = null;
        FileSummary lastModifiedFile = task.getLastModifiedFile();
        if (lastModifiedFile != null) {
            lastModifiedFileDTO = FileSummaryDTO.fromFileSummary(lastModifiedFile);
        }

        return new TaskInfoDTO(
                task.getTaskId().toString(),
                task.getName(),
                task.getDescription(),
                task.getProjectId().toString(),
                project.getName(),
                UserInfoDTO.fromUserSummary(project.getAdministrator()),
                UserInfoDTO.fromUserSummary(task.getAdministrator()),
                new Timestamp(task.getCreationDate().getTime()),
                new Timestamp(task.getModificationDate().getTime()),
                participants,
                lastModifiedFileDTO,
                files
        );
    }

    private FileMetadataDTO getFileMetadataDTOWithEmptyVersionList(FileMetadata fileMetadata) {
        VersionSummaryDTO latestVersionDTO = null;
        VersionSummary latestVersion = fileMetadata.getLatestVersion();
        if (latestVersion != null) {
            latestVersionDTO = VersionSummaryDTO.fromVersionSummaryEntity(latestVersion);
        }
        return new FileMetadataDTO(
                fileMetadata.getFileId().toString(),
                fileMetadata.getName(),
                fileMetadata.getDescription(),
                fileMetadata.getContentType().name(),
                fileMetadata.getContentType().getExtension(),
                fileMetadata.isConfirmed(),
                fileMetadata.isMarkedToConfirm(),
                new Timestamp(fileMetadata.getCreationDate().getTime()),
                new Timestamp(fileMetadata.getModificationDate().getTime()),
                latestVersionDTO,
                fileMetadata.getNumberOfVersions(),
                Collections.emptyList()
        );
    }

    @Override
    @EventListener
    @Order(PRECEDENCE_ORDER)
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public void onFileChangedEvent(FileChangedEvent event) throws ResourceNotFoundException {
        UUID projectId = event.getProjectId();
        UUID taskId = event.getTaskId();
        Task task = getTask(projectId, taskId);
        List<FileMetadata> files = fileMetadataRepository.findByTaskId(taskId).collect(Collectors.toList());
        task.setNumberOfFiles(files.size());
        FileSummary changedFileSummary = files.stream()
                .max(Comparator.comparing(fileMetadata -> fileMetadata.getLatestVersion().getSaveDate()))
                .map(FileSummary::new)
                .orElse(null);
        task.setLastModifiedFile(changedFileSummary);
        taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(UUID projectId, String taskName) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        return tasks.stream()
                .map(Task::getName)
                .anyMatch(taskName::equals);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO getTaskAdministrator(UUID projectId, UUID taskId) throws TaskNotFoundException {
        return taskRepository.findTaskByProjectIdAndTaskId(projectId, taskId)
                .map(task -> UserInfoDTO.fromUserSummary(task.getAdministrator()))
                .orElseThrow(() -> new TaskNotFoundException(taskId.toString()));
    }

    @Override
    public List<UserInfoDTO> removeParticipantFromTask(String email, UUID projectId, UUID taskId)
            throws ResourceNotFoundException {
        User user = userService.getUserByEmail(email);
        Task task = getTask(projectId, taskId);
        UserSummary newParticipant = new UserSummary(user);
        Set<UserSummary> currentParticipants = task.getParticipants();
        boolean removed = currentParticipants.remove(newParticipant);
        return updateParticipantsAndGetDTOs(task, currentParticipants, removed);
    }

    private List<UserInfoDTO> updateParticipantsAndGetDTOs(Task task,
                                                           Set<UserSummary> participants,
                                                           boolean changed) {
        task.setParticipants(participants);
        task = taskRepository.save(task);
        if (changed) {
            TaskChangedEvent event = new TaskChangedEvent(this, task.getProjectId(), task.getTaskId());
            applicationEventPublisher.publishEvent(event);
        }
        return task.getParticipants().stream()
                .map(UserInfoDTO::fromUserSummary)
                .sorted(Comparator.comparing(UserInfoDTO::getFullName))
                .collect(Collectors.toList());
    }

}
