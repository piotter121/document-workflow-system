package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.bson.types.ObjectId;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.*;

import java.util.List;

/**
 * Created by piotr on 29.12.16.
 */
public interface ProjectService {
    List<ProjectSummaryDTO> getUserParticipatedProjects(String userEmail) throws UserNotFoundException;

    ObjectId createNewProjectFromForm(NewProjectForm form);

    void deleteProject(ObjectId projectId) throws ProjectNotFoundException;

    ProjectInfoDTO getProjectInfo(ObjectId projectId) throws ProjectNotFoundException;

    String getProjectName(ObjectId projectId) throws ProjectNotFoundException;

    void processFileCreatedEvent(FileCreatedEvent event);

    void processFileDeletedEvent(FileDeletedEvent event);

    void processTaskCreatedEvent(TaskCreatedEvent event);

    void processTaskDeletedEvent(TaskDeletedEvent event);

    void processParticipantAddedToTaskEvent(ParticipantAddedToTaskEvent event);

    void processParticipantRemovedFromTaskEvent(ParticipantRemovedFromTaskEvent event);

    void processVersionCreatedEvent(VersionCreatedEvent event);
}
