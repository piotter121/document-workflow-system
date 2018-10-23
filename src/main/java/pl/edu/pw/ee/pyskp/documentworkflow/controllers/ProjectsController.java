package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.project.ProjectSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.search.SearchResultEntry;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FileSearchService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by piotr on 16.12.16.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectsController {
    @NonNull
    private final ProjectService projectService;

    @NonNull
    private final UserService userService;

    @NonNull
    private final FileSearchService fileSearchService;

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
        if (log.isDebugEnabled()) {
            log.debug("Received HTTP DELETE request for deletion project " + projectId);
        }
        projectService.deleteProject(projectId);
    }

    @GetMapping("/{projectId}/search")
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public List<SearchResultEntry> searchInProjectFiles(@RequestParam(name = "phrase") String searchPhrase,
                                                        @PathVariable ObjectId projectId) {
        return fileSearchService.searchInProject(projectId, searchPhrase);
    }
}
