package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by piotr on 29.12.16.
 */
public interface ProjectService {
    Optional<Project> getOneById(Long id);

    Project createNewProjectFromForm(NewProjectForm form);

    static List<ProjectInfoDTO> mapAllToProjectInfoDTO(Collection<Project> projects) {
        return projects != null
                ? projects.parallelStream().map(ProjectService::mapToProjectInfoDTO).collect(Collectors.toList())
                : Collections.emptyList();
    }

    static ProjectInfoDTO mapToProjectInfoDTO(Project project) {
        ProjectInfoDTO dto = new ProjectInfoDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setCreationDate(project.getCreationDate());
        dto.setAdministrator(UserService.mapToUserInfoDTO(project.getAdministrator()));
        project.getLastModifiedTask().map(TaskService::mapToTaskInfoDto).ifPresent(dto::setLastModifiedTask);
        dto.setTasks(TaskService.mapAllToTaskInfoDTO(project.getTasks()));
        return dto;
    }

    void deleteProject(long projectId);
}
