package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateProjectFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
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

    public ProjectsController(ProjectService projectService,
                              UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public String getUserProjects(Model model,
                                  @RequestParam(required = false) String onlyOwned,
                                  Principal principal) {
        String login = principal.getName();
        Set<Project> projects = new HashSet<>(projectService.findAllAdministratedProjects(login));
        if (onlyOwned == null)
            projects.addAll(projectService.findAllParticipatedProjects(login));
        model.addAttribute("projects", ProjectService.mapAllToProjectInfoDTO(projects));
        return "projects";
    }

    @GetMapping("/add")
    public String getNewProjectForm(@ModelAttribute("newProject") CreateProjectFormDTO newProject) {
        return "addProject";
    }

    @PostMapping("/add")
    public String processCreationOfNewProject(
            @ModelAttribute("newProject") @Valid CreateProjectFormDTO newProject,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "addProject";
        Project createdProject = projectService.createNewProjectFromForm(newProject);
        return String.format("redirect:/projects/%d", createdProject.getId());
    }

    @GetMapping("/{projectId}")
    public String showProjectInfo(@PathVariable Long projectId,
                                  Model model) {
        Optional<Project> projectOpt = projectService.getOneById(projectId);
        if (!projectOpt.isPresent()) throw new ProjectNotFoundException(projectId);
        Project project = projectOpt.get();
        model.addAttribute("project", ProjectService.mapToProjectInfoDTO(project));
        model.addAttribute("currentUser", UserService.mapToUserInfoDTO(userService.getCurrentUser()));
        return "project";
    }
}
