package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.bson.types.ObjectId;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectSummaryDTO;
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

    void deleteProject(ObjectId projectId);

    ProjectInfoDTO getProjectInfo(ObjectId projectId) throws ProjectNotFoundException;

    String getProjectName(ObjectId projectId) throws ProjectNotFoundException;

    @SuppressWarnings("unused")
    void processFileCreatedEvent(FileCreatedEvent event);

    @SuppressWarnings("unused")
    void processFileDeletedEvent(FileDeletedEvent event);

    @SuppressWarnings("unused")
    void processTaskCreatedEvent(TaskCreatedEvent event);

    @SuppressWarnings("unused")
    void processTaskDeletedEvent(TaskDeletedEvent event);

    @SuppressWarnings("unused")
    void processParticipantAddedToTaskEvent(ParticipantAddedToTaskEvent event);

    @SuppressWarnings("unused")
    void processParticipantRemovedFromTaskEvent(ParticipantRemovedFromTaskEvent event);

    @SuppressWarnings("unused")
    void processVersionCreatedEvent(VersionCreatedEvent event);
}
