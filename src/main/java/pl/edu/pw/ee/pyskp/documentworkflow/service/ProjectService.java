package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateProjectFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 29.12.16.
 */
public interface ProjectService {
    Optional<Project> getOneByName(String name);

    List<ProjectInfoDTO> findAllAdministratedProjects(String username) throws UserNotFoundException;

    List<ProjectInfoDTO> findAllProjectsWhereUserIsParticipant(String username) throws UserNotFoundException;

    Project createNewProjectFromForm(CreateProjectFormDTO formDTO);
}
