package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateProjectFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.PermissionDeniedException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import javax.validation.Valid;
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
    public String showProjectInfo(@PathVariable Long projectId, Model model,
                                  @RequestParam(required = false) String deleted) {
        User currentUser = userService.getCurrentUser();
        Optional<Project> projectOpt = projectService.getOneById(projectId);
        if (!projectOpt.isPresent()) throw new ProjectNotFoundException(projectId);
        Project project = projectOpt.get();

        if (!currentUser.hasAccessToProject(project))
            throw new PermissionDeniedException(
                    String.format("Użytkownik o nazwie %s nie posiada uprawnień do projektu o ID = %d",
                            currentUser.getLogin(), project.getId()));

        model.addAttribute("project", ProjectService.mapToProjectInfoDTO(project));
        model.addAttribute("currentUser", UserService.mapToUserInfoDTO(currentUser));
        if (deleted != null) model.addAttribute("delete", "success");
        return "project";
    }
}
