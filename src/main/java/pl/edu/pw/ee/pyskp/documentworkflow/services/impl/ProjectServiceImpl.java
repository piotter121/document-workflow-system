package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.events.TaskChangedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by piotr on 29.12.16.
 */
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    @NonNull
    private final UserService userService;

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final UserProjectRepository userProjectRepository;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectSummaryDTO> getUserParticipatedProjects(String userEmail) {
        return userProjectRepository.findAllByUserEmail(userEmail)
                .map(this::toProjectSummaryDTO)
                .collect(Collectors.toList());
    }

    private ProjectSummaryDTO toProjectSummaryDTO(UserProject userProject) {
        FileSummaryDTO lastModifiedFileDTO = null;
        FileSummary lastModifiedFile = userProject.getLastModifiedFile();
        if (lastModifiedFile != null) {
            lastModifiedFileDTO = FileSummaryDTO.fromFileSummary(lastModifiedFile);
        }
        return new ProjectSummaryDTO(
                userProject.getProjectId().toString(),
                userProject.getName(),
                new Timestamp(userProject.getCreationDate().getTime()),
                userProject.getNumberOfParticipants(),
                userProject.getNumberOfTasks(),
                userProject.getNumberOfFiles(),
                lastModifiedFileDTO
        );
    }

    @Override
    @Transactional
    public UUID createNewProjectFromForm(NewProjectForm formDTO) {
        User currentUser = userService.getCurrentUser();
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setName(formDTO.getName().trim());
        project.setDescription(formDTO.getDescription().trim());
        project.setAdministrator(new UserSummary(currentUser));
        project.setCreationDate(new Date());
        Project createdProject = projectRepository.save(project);
        UserProject userProject = new UserProject(currentUser, createdProject);
        userProjectRepository.save(userProject);
        return createdProject.getId();
    }

    @Override
    @Transactional
    public void deleteProject(UUID projectId) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        List<UUID> tasksIds = tasks.stream()
                .map(Task::getTaskId)
                .collect(Collectors.toList());

        Set<String> participantsEmails = tasks.stream()
                .flatMap(task -> task.getParticipants().stream())
                .map(UserSummary::getEmail)
                .collect(Collectors.toSet());
        Set<String> administratorsEmails = tasks.stream()
                .map(task -> task.getAdministrator().getEmail())
                .collect(Collectors.toSet());
        participantsEmails.addAll(administratorsEmails);

        List<UUID> filesIds = fileMetadataRepository.findByTaskIdIn(tasksIds)
                .map(FileMetadata::getFileId)
                .collect(Collectors.toList());
        versionRepository.deleteByFileIdIn(filesIds);
        fileMetadataRepository.deleteByTaskIdIn(tasksIds);
        taskRepository.deleteAllByProjectId(projectId);
        projectRepository.deleteById(projectId);

        String currentUserEmail = userService.getCurrentUserEmail();
        userProjectRepository.deleteUserProjectByUserEmailAndProjectId(currentUserEmail, projectId);
        userProjectRepository.deleteAllByUserEmailInAndProjectId(participantsEmails, projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectInfoDTO getProjectInfo(UUID projectId) throws ProjectNotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        List<Task> projectTasks = taskRepository.findAllByProjectId(projectId);
        projectTasks.sort(Comparator.comparing(Task::getModificationDate));

        Timestamp modificationDate = projectTasks.stream()
                .findFirst()
                .map(task -> new Timestamp(task.getModificationDate().getTime()))
                .orElseGet(() -> new Timestamp(project.getCreationDate().getTime()));

        List<TaskSummaryDTO> taskSummaries = projectTasks.stream()
                .map(this::toTaskSummaryDTO)
                .collect(Collectors.toList());

        return new ProjectInfoDTO(
                project.getId().toString(),
                project.getName(),
                project.getDescription(),
                UserInfoDTO.fromUserSummary(project.getAdministrator()),
                new Timestamp(project.getCreationDate().getTime()),
                modificationDate,
                taskSummaries
        );
    }

    private TaskSummaryDTO toTaskSummaryDTO(Task task) {
        FileSummary lastModifiedFile = task.getLastModifiedFile();
        FileSummaryDTO lastModifiedFileDTO = null;
        if (lastModifiedFile != null) {
            lastModifiedFileDTO = FileSummaryDTO.fromFileSummary(lastModifiedFile);
        }
        return new TaskSummaryDTO(
                task.getTaskId().toString(),
                task.getName(),
                new Timestamp(task.getCreationDate().getTime()),
                lastModifiedFileDTO,
                task.getNumberOfFiles(),
                task.getParticipants().size()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public String getProjectName(UUID projectId) throws ProjectNotFoundException {
        return projectRepository.findById(projectId)
                .map(Project::getName)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
    }

    @Override
    @EventListener
    @Order(PRECEDENCE_ORDER)
    @Transactional
    public void onTaskChangedEvent(TaskChangedEvent event) throws ProjectNotFoundException {
        UUID projectId = event.getProjectId();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        Set<String> projectParticipants = getProjectParticipants(project);
        List<UserProject> userProjects =
                userProjectRepository.findAllByUserEmailInAndProjectId(projectParticipants, projectId);
        Set<String> currentUserProjectsEmails = userProjects.stream()
                .map(UserProject::getUserEmail)
                .collect(Collectors.toSet());
        List<UserProject> newUserProjects =
                createMissingUserProjects(project, projectParticipants, currentUserProjectsEmails);
        userProjects.addAll(newUserProjects);
        FileSummary lastModifiedFile = getLastModifiedFileSummary(tasks);
        long numberOfParticipants = projectParticipants.size();
        long numberOfTasks = tasks.size();
        long numberOfFiles = tasks.stream().mapToLong(Task::getNumberOfFiles).sum();
        for (UserProject userProject : userProjects) {
            userProject.setLastModifiedFile(lastModifiedFile);
            userProject.setNumberOfParticipants(numberOfParticipants);
            userProject.setNumberOfTasks(numberOfTasks);
            userProject.setNumberOfFiles(numberOfFiles);
        }
        userProjectRepository.saveAll(userProjects);
    }

    private Set<String> getProjectParticipants(Project project) {
        List<Task> tasks = taskRepository.findAllByProjectId(project.getId());
        return getParticipantsEmailsDistinctStream(project, tasks).collect(Collectors.toSet());
    }

    private Stream<String> getParticipantsEmailsDistinctStream(Project project, List<Task> tasks) {
        Stream<String> participantsEmailsStream = tasks.stream()
                .filter(task -> Objects.nonNull(task.getParticipants()))
                .flatMap(task -> task.getParticipants().stream())
                .map(UserSummary::getEmail)
                .distinct();
        Stream<String> administratorsEmailsStream = tasks.stream()
                .map(task -> task.getAdministrator().getEmail())
                .distinct();
        return Stream.concat(
                Stream.concat(participantsEmailsStream, administratorsEmailsStream).distinct(),
                Stream.of(project.getAdministrator().getEmail())
        ).distinct();
    }

    private List<UserProject> createMissingUserProjects(Project project, Set<String> projectParticipants,
                                                        Set<String> currentUserProjectsEmails) {
        Set<String> toCreate = new HashSet<>(projectParticipants);
        toCreate.removeAll(currentUserProjectsEmails);
        List<User> users = userRepository.findAllById(toCreate);
        return users.stream()
                .map(user -> new UserProject(user, project))
                .collect(Collectors.toList());
    }

    private FileSummary getLastModifiedFileSummary(List<Task> projectTasks) {
        return projectTasks.stream()
                .map(Task::getLastModifiedFile)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(FileSummary::getModificationDate))
                .orElse(null);
    }
}
