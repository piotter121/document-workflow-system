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
import pl.edu.pw.ee.pyskp.documentworkflow.service.TaskService;
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
    private final TaskService taskService;
    private final UserService userService;

    public ProjectsController(ProjectService projectService, TaskService taskService, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String getUserProjects(Model model, @RequestParam(required = false) String onlyOwned) {
        User currentUser = userService.getCurrentUser();
        Set<Project> projects = new HashSet<>(currentUser.getAdministratedProjects());
        if (onlyOwned == null) {
            projects.addAll(currentUser.getParticipatedProjects());
        }
        model.addAttribute("projects", ProjectService.mapAllToProjectInfoDTO(projects));
        return "projects";
    }

    @GetMapping("/add")
    public String getNewProjectForm(@ModelAttribute("newProject") NewProjectForm newProject) {
        return "addProject";
    }

    @PostMapping("/add")
    public String processCreationOfNewProject(
            @ModelAttribute("newProject") @Valid NewProjectForm newProject,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "addProject";
        Project createdProject = projectService.createNewProjectFromForm(newProject);
        return String.format("redirect:/projects/%d", createdProject.getId());
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public String showProjectInfo(@PathVariable Long projectId, Model model,
                                  @RequestParam(required = false) String deleted) {
        ProjectInfoDTO project = projectService.getOneById(projectId)
                .map(ProjectService::mapToProjectInfoDTO)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        model.addAttribute("project", project);
        model.addAttribute("currentUser", UserService.mapToUserInfoDTO(userService.getCurrentUser()));
        if (deleted != null) model.addAttribute("delete", "success");
        return "project";
    }
}
