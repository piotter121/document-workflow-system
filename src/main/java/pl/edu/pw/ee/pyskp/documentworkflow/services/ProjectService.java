package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;

import java.util.List;

/**
 * Created by piotr on 29.12.16.
 */
public interface ProjectService {
    Project getProject(Long projectId) throws ProjectNotFoundException;

    List<ProjectSummaryDTO> getUserParticipatedProjects(String userEmail);

    Long createNewProjectFromForm(NewProjectForm form);

    void deleteProject(Long projectId);

    ProjectInfoDTO getProjectInfo(Long projectId) throws ProjectNotFoundException;

    String getProjectName(Long projectId) throws ProjectNotFoundException;
}
