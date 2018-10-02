package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by piotr on 16.12.16.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/projects")
public class ProjectsController {
    @NonNull
    private final ProjectService projectService;

    @NonNull
    private final UserService userService;

    @GetMapping
    public List<ProjectSummaryDTO> getUserProjects() {
        String currentUserEmail = userService.getCurrentUserEmail();
        return projectService.getUserParticipatedProjects(currentUserEmail);
    }

    @PostMapping
    public Long processCreationOfNewProject(@RequestBody @Valid NewProjectForm newProject) {
        return projectService.createNewProjectFromForm(newProject);
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public ProjectInfoDTO getProjectInfo(@PathVariable Long projectId) throws ProjectNotFoundException {
        return projectService.getProjectInfo(projectId);
    }

    @GetMapping(value = "/{projectId}/name", produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public String getProjectName(@PathVariable Long projectId) throws ProjectNotFoundException {
        return projectService.getProjectName(projectId);
    }

    @DeleteMapping("/{projectId}")
    @PreAuthorize("@securityService.isCurrentUserProjectAdministrator(#projectId)")
    public void deleteProject(@PathVariable Long projectId) {
        if (log.isDebugEnabled()) {
            log.debug("Received HTTP DELETE request for project deletion (id = " + projectId + ")");
        }
        projectService.deleteProject(projectId);
    }
}
