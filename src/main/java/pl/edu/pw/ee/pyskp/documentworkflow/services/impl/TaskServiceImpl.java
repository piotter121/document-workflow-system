package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.NewTaskForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    private final FilesMetadataService filesService;

    @NonNull
    private final VersionService versionService;

    @NonNull
    private final ProjectRepository projectRepository;

    @Override
    public Long createTaskFromForm(NewTaskForm form, Long projectId) throws ResourceNotFoundException {
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(projectId.toString());
        }
        Task task = new Task();
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setCreationDate(getNowTimestamp());
        task.setProject(project);
        String administratorEmail = form.getAdministratorEmail();
        task.setAdministrator(userService.getUserByEmail(administratorEmail));
        return taskRepository.save(task).getId();
    }

    private Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    @Override
    public void deleteTask(Long taskId) {
        versionService.deleteTaskFilesVersions(taskId);
        taskRepository.delete(taskId);
    }

    @Override
    public List<UserInfoDTO> addParticipantToTask(String userEmail, Long taskId) throws ResourceNotFoundException {
        User user = userService.getUserByEmail(userEmail);
        Task task = getTask(taskId);
        if (!task.getParticipants().contains(user)) {
            task.getParticipants().add(user);
        }
        task = taskRepository.save(task);
        return task.getParticipants().stream()
                .map(UserInfoDTO::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public TaskInfoDTO getTaskInfo(Long taskId) throws TaskNotFoundException {
        Task task = getTask(taskId);
        Project project = task.getProject();
        User projectAdministrator = project.getAdministrator();
        User administrator = task.getAdministrator();
        return new TaskInfoDTO(
                task.getId().toString(),
                task.getName(),
                task.getDescription(),
                project.getId().toString(),
                project.getName(),
                UserInfoDTO.fromUser(projectAdministrator),
                UserInfoDTO.fromUser(administrator),
                task.getCreationDate(),
                getModificationDate(task),
                getUserInfo(task.getParticipants()),
                getLastModifiedFile(task.getFiles()),
                getFileInfoDTOs(task)
        );
    }

    private List<FileMetadataDTO> getFileInfoDTOs(Task task) {
        List<FileMetadata> files = task.getFiles();
        return files.stream()
                .map(fileMetadata -> new FileMetadataDTO(
                        fileMetadata.getId().toString(),
                        fileMetadata.getName(),
                        fileMetadata.getDescription(),
                        fileMetadata.getContentType().getName(),
                        fileMetadata.getContentType().getExtension(),
                        fileMetadata.getConfirmed(),
                        fileMetadata.getMarkedToConfirm(),
                        fileMetadata.getCreationDate(),
                        fileMetadata.getLatestVersion().getSaveDate(),
                        VersionSummaryDTO.fromVersionSummaryEntity(fileMetadata.getLatestVersion()),
                        getNumberOfVersions(fileMetadata),
                        Collections.emptyList()
                )).collect(Collectors.toList());
    }

    private int getNumberOfVersions(FileMetadata fileMetadata) {
        return versionService.getNumberOfVersions(fileMetadata);
    }

    private FileSummaryDTO getLastModifiedFile(Collection<FileMetadata> files) {
        return files.stream()
                .max(Comparator.comparing(file -> file.getLatestVersion().getSaveDate()))
                .map(file -> new FileSummaryDTO(
                        file.getName(),
                        file.getLatestVersion().getSaveDate(),
                        file.getLatestVersion().getModificationAuthor().getFullName(),
                        file.getTask().getName()
                )).orElse(null);
    }

    private Timestamp getModificationDate(Task task) {
        return task.getFiles().stream()
                .map(fileMetadata -> fileMetadata.getLatestVersion().getSaveDate())
                .max(Timestamp::compareTo)
                .orElse(task.getCreationDate());
    }

    private List<UserInfoDTO> getUserInfo(List<User> participants) {
        return participants.stream()
                .map(UserInfoDTO::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(Long projectId, String taskName) {
        return taskRepository.existsByProject_IdAndName(projectId, taskName);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO getTaskAdministrator(Long taskId) throws TaskNotFoundException {
        Task task = getTask(taskId);
        return UserInfoDTO.fromUser(task.getAdministrator());
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public List<UserInfoDTO> removeParticipantFromTask(String email, Long taskId) throws ResourceNotFoundException {
        User user = userService.getUserByEmail(email);
        Task task = getTask(taskId);
        task.getParticipants().remove(user);
        task = taskRepository.save(task);
        return task.getParticipants().stream()
                .map(UserInfoDTO::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTask(Long taskId) throws TaskNotFoundException {
        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId.toString());
        }
        return task;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskSummaryDTO getTaskSummary(Task task) {
        return new TaskSummaryDTO(
                task.getId().toString(),
                task.getName(),
                task.getCreationDate(),
                getLastModifiedFile(task),
                getNumberOfFiles(task),
                getNumberOfParticipants(task)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfTasks(Project project) {
        return taskRepository.countDistinctByProject(project);
    }

    private int getNumberOfParticipants(Task task) {
        return userService.getNumberOfParticipants(task);
    }

    private int getNumberOfFiles(Task task) {
        return filesService.getNumberOfFiles(task);
    }

    private FileSummaryDTO getLastModifiedFile(Task task) {
        return filesService.getLastModifiedFileSummary(task).orElse(null);
    }

}
