package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.*;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.*;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by piotr on 29.12.16.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ProjectServiceImpl implements ProjectService {
    @NonNull
    private final UserService userService;

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final TaskService taskService;

    @NonNull
    private final FilesMetadataService filesMetadataService;

    @NonNull
    private final VersionService versionService;

    @Override
    @Transactional(readOnly = true)
    public Project getProject(Long projectId) throws ProjectNotFoundException {
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(projectId.toString());
        }
        return project;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectSummaryDTO> getUserParticipatedProjects(String userEmail) {
        return projectRepository.findParticipatedProjects(userEmail)
                .map(this::getProjectSummaryDTO)
                .sorted(Comparator
                        .comparing((Function<ProjectSummaryDTO, OffsetDateTime>) this::getModificationDate)
                        .reversed())
                .collect(Collectors.toList());
    }

    private OffsetDateTime getModificationDate(ProjectSummaryDTO projectSummary) {
        if (projectSummary.getLastModifiedFile() != null) {
            return projectSummary.getLastModifiedFile().getSaveDate();
        } else {
            return projectSummary.getCreationDate();
        }
    }

    private ProjectSummaryDTO getProjectSummaryDTO(Project project) {
        return new ProjectSummaryDTO(
                project.getId().toString(),
                project.getName(),
                project.getCreationDate(),
                getNumberOfParticipants(project),
                getNumberOfTasks(project),
                getNumberOfFiles(project),
                getLastModifiedFile(project)
        );
    }

    private int getNumberOfParticipants(Project project) {
        return userService.getNumberOfParticipants(project);
    }

    private int getNumberOfTasks(Project project) {
        return taskService.getNumberOfTasks(project);
    }

    private int getNumberOfFiles(Project project) {
        return filesMetadataService.getNumberOfFiles(project);
    }

    private FileSummaryDTO getLastModifiedFile(Project project) {
        return filesMetadataService.getLastModifiedFileSummary(project).orElse(null);
    }

    @Override
    @Transactional
    public Long createNewProjectFromForm(NewProjectForm formDTO) {
        User currentUser = userService.getCurrentUser();
        Project project = new Project();
        project.setName(formDTO.getName().trim());
        project.setDescription(formDTO.getDescription().trim());
        project.setAdministrator(currentUser);
        project.setCreationDate(OffsetDateTime.now());
        return projectRepository.save(project).getId();
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        versionService.deleteProjectFilesVersions(projectId);
        projectRepository.delete(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectInfoDTO getProjectInfo(Long projectId) throws ProjectNotFoundException {
        Project project = projectRepository.findOneByIdFetchedTasks(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
        User administrator = project.getAdministrator();
        OffsetDateTime modificationDate = getLastModifiedFile(project).getSaveDate();
        List<TaskSummaryDTO> taskSummaryDTOS = project.getTasks().stream()
                .map(taskService::getTaskSummary)
                .sorted(Comparator
                        .comparing((Function<TaskSummaryDTO, OffsetDateTime>) this::getModificationDate)
                        .reversed())
                .collect(Collectors.toList());
        return new ProjectInfoDTO(
                project.getId().toString(),
                project.getName(),
                project.getDescription(),
                UserInfoDTO.fromUser(administrator),
                project.getCreationDate(),
                modificationDate,
                taskSummaryDTOS
        );
    }

    private OffsetDateTime getModificationDate(TaskSummaryDTO taskSummaryDTO) {
        if (taskSummaryDTO.getLastModifiedFile() != null) {
            return taskSummaryDTO.getLastModifiedFile().getSaveDate();
        } else {
            return taskSummaryDTO.getCreationDate();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getProjectName(Long projectId) throws ProjectNotFoundException {
        return projectRepository.findNameById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));
    }

}
