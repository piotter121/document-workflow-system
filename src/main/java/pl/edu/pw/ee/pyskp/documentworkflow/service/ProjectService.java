package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateProjectFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UserNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by piotr on 29.12.16.
 */
public interface ProjectService {
    ProjectInfoDTO getOneByName(String name) throws ProjectNotFoundException;

    List<ProjectInfoDTO> findAllAdministratedProjects(String username) throws UserNotFoundException;

    List<ProjectInfoDTO> findAllProjectsWhereUserIsParticipant(String username) throws UserNotFoundException;

    Project createNewProjectFromForm(CreateProjectFormDTO formDTO);

    static List<ProjectInfoDTO> mapAllToProjectInfoDTO(Collection<Project> projects) {
        return projects != null
                ? projects.parallelStream().map(ProjectService::mapToProjectInfoDTO).collect(Collectors.toList())
                : Collections.emptyList();
    }

    static ProjectInfoDTO mapToProjectInfoDTO(Project project) {
        ProjectInfoDTO dto = new ProjectInfoDTO();
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setCreationDate(project.getCreationDate());
        dto.setAdministratorName(project.getAdministrator().getFullName());
        project.getLastModified().ifPresent(dto::setLastModified);
        dto.setTasks(TaskService.mapAllToTaskInfoDTO(project.getTasks()));
        return dto;
    }

    boolean existsProjectWithName(String name);
}
