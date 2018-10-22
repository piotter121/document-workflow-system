package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by piotr on 16.12.16.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/projects")
public class ProjectsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsController.class);

    @NonNull
    private final ProjectService projectService;

    @NonNull
    private final UserService userService;

    @GetMapping
    public List<ProjectSummaryDTO> getUserProjects() throws UserNotFoundException {
        User currentUser = userService.getCurrentUser();
        return projectService.getUserParticipatedProjects(currentUser.getEmail());
    }

    @PostMapping
    public String processCreationOfNewProject(@RequestBody @Valid NewProjectForm newProject) {
        ObjectId createdProjectId = projectService.createNewProjectFromForm(newProject);
        return createdProjectId.toString();
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public ProjectInfoDTO getProjectInfo(@PathVariable ObjectId projectId) throws ProjectNotFoundException {
        return projectService.getProjectInfo(projectId);
    }

    @GetMapping(value = "/{projectId}/name", produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public String getProjectName(@PathVariable ObjectId projectId) throws ProjectNotFoundException {
        return projectService.getProjectName(projectId);
    }

    @DeleteMapping("/{projectId}")
    @PreAuthorize("@securityService.isCurrentUserProjectAdministrator(#projectId)")
    public void deleteProject(@PathVariable ObjectId projectId) throws ProjectNotFoundException {
        LOGGER.debug("Received HTTP DELETE request for deletion project " + projectId);
        projectService.deleteProject(projectId);
    }
}
