package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.events.TaskChangedEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by piotr on 29.12.16.
 */
public interface ProjectService {
    int PRECEDENCE_ORDER = 3;

    List<ProjectSummaryDTO> getUserParticipatedProjects(String userEmail);

    UUID createNewProjectFromForm(NewProjectForm form);

    void deleteProject(UUID projectId);

    ProjectInfoDTO getProjectInfo(UUID projectId) throws ProjectNotFoundException;

    String getProjectName(UUID projectId) throws ProjectNotFoundException;

    @SuppressWarnings("unused")
    void onTaskChangedEvent(TaskChangedEvent event) throws ProjectNotFoundException;
}
