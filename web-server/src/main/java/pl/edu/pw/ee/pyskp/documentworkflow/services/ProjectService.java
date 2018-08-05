package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by piotr on 29.12.16.
 */
public interface ProjectService {
    List<ProjectSummaryDTO> getUserParticipatedProjects(String userEmail);

    UUID createNewProjectFromForm(NewProjectForm form);

    void deleteProject(UUID projectId);

    ProjectInfoDTO getProjectInfo(UUID projectId) throws ProjectNotFoundException;

    String getProjectName(UUID projectId) throws ProjectNotFoundException;

    void updateProjectStatisticsForItsUsers(UUID projectId) throws ProjectNotFoundException;
}
