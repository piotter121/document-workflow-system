package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

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
    public List<ProjectSummaryDTO> getUserParticipatedProjects(String userLogin) {
        Stream<UserProject> userProjects = userProjectRepository.findAllByUserLogin(userLogin);
        return userProjects.map(ProjectSummaryDTO::new).collect(Collectors.toList());
    }

    @Override
    public UUID createNewProjectFromForm(NewProjectForm formDTO) {
        User currentUser = userService.getCurrentUser();
        Project project = new Project();
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
    public void deleteProject(UUID projectId) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        List<UUID> tasksIds = tasks.stream()
                .map(Task::getTaskId).collect(Collectors.toList());
        Set<String> participantsLogins = tasks.stream().flatMap(task -> task.getParticipants().stream())
                .map(UserSummary::getLogin)
                .distinct()
                .collect(Collectors.toSet());
        participantsLogins.addAll(tasks.stream()
                .map(task -> task.getAdministrator().getLogin())
                .collect(Collectors.toList()));
        List<UUID> filesIds = fileMetadataRepository.findAllByTaskIdIn(tasksIds).stream()
                .map(FileMetadata::getFileId).collect(Collectors.toList());
        versionRepository.deleteAllByFileIdIn(filesIds);
        fileMetadataRepository.deleteAllByTaskIdIn(tasksIds);
        taskRepository.deleteAllByProjectId(projectId);
        projectRepository.deleteOneById(projectId);

        String currentUserLogin = userService.getCurrentUserLogin();
        userProjectRepository.deleteUserProjectByUserLoginAndProjectId(currentUserLogin, projectId);
        userProjectRepository.deleteAllByUserLoginInAndProjectId(participantsLogins, projectId);
    }

    @Override
    public ProjectInfoDTO getProjectInfo(UUID projectId) {
        Project project = projectRepository.findOneById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        List<Task> projectTasks = taskRepository.findAllByProjectId(projectId);
        return new ProjectInfoDTO(project, projectTasks);
    }

    @Override
    public String getProjectName(UUID projectId) {
        return projectRepository.findOneById(projectId)
                .map(Project::getName)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    @Override
    public void updateProjectStatisticsForItsUsers(UUID projectId) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        Set<String> projectParticipants = getProjectParticipants(projectId);
        List<UserProject> userProjects = userProjectRepository
                .findAllByUserLoginInAndProjectId(projectParticipants, projectId);
        Set<String> currentUserProjectsLogins = userProjects.stream().map(UserProject::getUserLogin)
                .distinct().collect(Collectors.toSet());
        List<UserProject> newUserProjects =
                createMissingUserProjects(projectId, projectParticipants, currentUserProjectsLogins);
        userProjects.addAll(newUserProjects);
        FileSummary lastModifiedFile = tasks.stream()
                .map(Task::getLastModifiedFile)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(FileSummary::getModificationDate))
                .orElse(null);
        long numberOfParticipants = projectParticipants.size();
        long numberOfTasks = tasks.size();
        long numberOfFiles = tasks.stream().mapToLong(Task::getNumberOfFiles)
                .sum();
        for (UserProject userProject : userProjects) {
            userProject.setLastModifiedFile(lastModifiedFile);
            userProject.setNumberOfParticipants(numberOfParticipants);
            userProject.setNumberOfTasks(numberOfTasks);
            userProject.setNumberOfFiles(numberOfFiles);
        }
        userProjectRepository.save(userProjects);
    }

    private List<UserProject> createMissingUserProjects(UUID projectId, Set<String> projectParticipants,
                                                        Set<String> currentUserProjectsLogins) {
        Set<String> toCreate = new HashSet<>(projectParticipants);
        toCreate.removeAll(currentUserProjectsLogins);
        List<User> users = userRepository.findAllByLoginIn(toCreate);
        Project project = projectRepository.findOneById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        return users.stream().map(user -> new UserProject(user, project))
                .collect(Collectors.toList());
    }

    private Set<String> getProjectParticipants(UUID projectId) {
        String administrator = projectRepository.findOneById(projectId)
                .map(project -> project.getAdministrator().getLogin())
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        Stream<String> participantsLoginsStream = tasks.stream()
                .filter(task -> Objects.nonNull(task.getParticipants()))
                .flatMap(task -> task.getParticipants().stream())
                .map(UserSummary::getLogin);
        Stream<String> administratorsStream = tasks.stream()
                .map(task -> task.getAdministrator().getLogin());
        Set<String> toReturn = Stream.concat(participantsLoginsStream, administratorsStream).distinct()
                .collect(Collectors.toSet());
        toReturn.add(administrator);
        return toReturn;
    }
}
