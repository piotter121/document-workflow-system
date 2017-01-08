package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by piotr on 16.12.16.
 */
@Controller
@RequestMapping("/projects")
public class ProjectsController {
    private static final Logger logger = Logger.getLogger(ProjectsController.class);

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectsController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public String getUserProjects(Model model, @RequestParam(required = false) String onlyOwned) {
        User currentUser = userService.getCurrentUser();
        Set<Project> projects = new HashSet<>(currentUser.getAdministratedProjects());
        if (onlyOwned == null) {
            projects.addAll(currentUser.getParticipatedProjects());
        }
        addCurrentUserToModel(model);
        model.addAttribute("projects", ProjectService.mapAllToProjectInfoDTO(projects));
        return "projects";
    }

    private void addCurrentUserToModel(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", UserService.mapToUserInfoDTO(currentUser));
    }

    @GetMapping("/add")
    public String getNewProjectForm(@ModelAttribute NewProjectForm newProject, Model model) {
        addCurrentUserToModel(model);
        return "addProject";
    }

    @PostMapping("/add")
    public String processCreationOfNewProject(@ModelAttribute @Valid NewProjectForm newProject,
                                              BindingResult bindingResult,
                                              Model model) {
        if (bindingResult.hasErrors()) {
            addCurrentUserToModel(model);
            return "addProject";
        }
        long createdProjectId = projectService.createNewProjectFromForm(newProject).getId();
        return String.format("redirect:/projects/%d", createdProjectId);
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public String showProjectInfo(@PathVariable Long projectId, Model model,
                                  @RequestParam(required = false) String deleted) {
        ProjectInfoDTO project = projectService.getOneById(projectId)
                .map(ProjectService::mapToProjectInfoDTO)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        model.addAttribute("project", project);
        addCurrentUserToModel(model);
        if (deleted != null) model.addAttribute("delete", "success");
        return "project";
    }

    @DeleteMapping("/{projectId}")
    @PreAuthorize("@securityService.canDeleteProject(#projectId)")
    public String deleteProject(@PathVariable long projectId) {
        logger.debug("Received HTTP DELETE request for deletion project of id=" + projectId);
        projectService.deleteProject(projectId);
        return "redirect:/";
    }
}
