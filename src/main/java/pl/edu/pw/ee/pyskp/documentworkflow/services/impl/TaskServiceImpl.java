package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.*;

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
    private final ProjectRepository projectRepository;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final ApplicationEventPublisher applicationEventPublisher;

    private Task getTask(ObjectId taskId) throws TaskNotFoundException {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId.toString()));
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public ObjectId createTaskFromForm(NewTaskForm form, ObjectId projectId) throws ResourceNotFoundException {
        Project project = getProject(projectId);

        Task task = new Task();
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setCreationDate(new Date());
        task.setProject(project);

        String administratorEmail = form.getAdministratorEmail();
        User administrator = userService.getUserByEmail(administratorEmail);
        task.setAdministrator(administrator);

        task.setParticipants(Collections.emptyList());
        task.setNumberOfFiles(0);

        task = taskRepository.save(task);

        applicationEventPublisher.publishEvent(new TaskCreatedEvent(this, task));

        return task.getId();
    }

    private Project getProject(ObjectId projectId) throws ProjectNotFoundException {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
    }

    @Override
    @Transactional(rollbackFor = TaskNotFoundException.class)
    public void deleteTask(ObjectId taskId) throws TaskNotFoundException {
        Task taskToDelete = getTask(taskId);
        List<FileMetadata> taskFiles = fileMetadataRepository.findByTask(taskToDelete);
        versionRepository.deleteByFileIn(taskFiles);
        fileMetadataRepository.deleteByTask_Id(taskId);
        taskRepository.delete(taskToDelete);

        applicationEventPublisher.publishEvent(new TaskDeletedEvent(this, taskToDelete));
    }

    @Override
    @Transactional
    public List<UserInfoDTO> addParticipantToTask(String userEmail, ObjectId projectId, ObjectId taskId)
            throws ResourceNotFoundException {
        User newParticipant = userService.getUserByEmail(userEmail);
        Task task = getTask(taskId);
        Set<User> currentParticipants = new HashSet<>(task.getParticipants());
        boolean added = currentParticipants.add(newParticipant);
        task.setParticipants(new ArrayList<>(currentParticipants));
        task = taskRepository.save(task);
        if (added) {
            applicationEventPublisher.publishEvent(new ParticipantAddedToTaskEvent(this, newParticipant, task));
        }
        return task.getParticipants().stream()
                .map(UserInfoDTO::fromUser)
                .sorted(Comparator.comparing(UserInfoDTO::getFullName))
                .collect(Collectors.toList());
    }

    @Override
    public TaskInfoDTO getTaskInfo(ObjectId projectId, ObjectId taskId) throws ResourceNotFoundException {
        Task task = getTask(taskId);
        List<FileMetadata> files = fileMetadataRepository.findByTask(task);
        return TaskInfoDTO.fromTaskAndFiles(task, files);
    }

    @Override
    public boolean existsByName(ObjectId projectId, String taskName) {
        List<Task> tasks = taskRepository.findByProject_Id(projectId);
        return tasks.stream()
                .map(Task::getName)
                .anyMatch(taskName::equals);
    }

    @Override
    public UserInfoDTO getTaskAdministrator(ObjectId taskId) throws TaskNotFoundException {
        return taskRepository.findById(taskId)
                .map(task -> UserInfoDTO.fromUser(task.getAdministrator()))
                .orElseThrow(() -> new TaskNotFoundException(taskId.toString()));
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public List<UserInfoDTO> removeParticipantFromTask(String email, ObjectId taskId)
            throws ResourceNotFoundException {
        User userToRemove = userService.getUserByEmail(email);
        Task task = getTask(taskId);
        Set<User> currentParticipants = new HashSet<>(task.getParticipants());
        boolean removed = currentParticipants.remove(userToRemove);
        task.setParticipants(new ArrayList<>(currentParticipants));
        task = taskRepository.save(task);
        if (removed) {
            applicationEventPublisher.publishEvent(
                    new ParticipantRemovedFromTaskEvent(this, userToRemove, task)
            );
        }
        return task.getParticipants().stream()
                .map(UserInfoDTO::fromUser)
                .sorted(Comparator.comparing(UserInfoDTO::getFullName))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @EventListener
    @Order(1)
    public void processFileCreatedEvent(FileCreatedEvent event) {
        FileMetadata newFile = event.getCreatedFile();
        Task task = newFile.getTask();
        task.setLastModifiedFile(newFile);
        task.setNumberOfFiles(task.getNumberOfFiles() + 1);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    @EventListener
    @Order(1)
    public void processFileDeletedEvent(FileDeletedEvent event) {
        Task task = event.getDeletedFile().getTask();
        List<FileMetadata> files = fileMetadataRepository.findByTask(task);
        task.setNumberOfFiles(files.size());
        FileMetadata lastModifiedFile = files.stream()
                .max(Comparator.comparing(file -> file.getLatestVersion().getSaveDate()))
                .orElse(null);
        task.setLastModifiedFile(lastModifiedFile);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    @EventListener
    @Order(2)
    public void processVersionCreatedEvent(VersionCreatedEvent event) {
        FileMetadata modifiedFile = event.getModifiedFile();
        Task task = modifiedFile.getTask();
        task.setLastModifiedFile(modifiedFile);
        taskRepository.save(task);
    }

}
