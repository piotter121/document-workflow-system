package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateProjectFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.validator.CreateProjectFormValidator;

import java.security.Principal;

/**
 * Created by piotr on 16.12.16.
 */
@Controller
@RequestMapping("/projects")
public class ProjectsController {
    private static final Logger logger = Logger.getLogger(ProjectsController.class);

    private final ProjectService projectService;
    private final CreateProjectFormValidator createProjectFormValidator;

    public ProjectsController(ProjectService projectService, CreateProjectFormValidator createProjectFormValidator) {
        this.projectService = projectService;
        this.createProjectFormValidator = createProjectFormValidator;
    }

    @GetMapping
    public String getUserProjects(Model model, Principal principal) {
        String login = principal.getName();
        // TODO zrobić filtr projektów
        return "projects";
    }

    @GetMapping("/add")
    public String getNewProjectForm(@ModelAttribute("newProject") CreateProjectFormDTO newProject) {
        return "addProject";
    }

    @PostMapping("/add")
    public String processCreationOfNewProject(
            @ModelAttribute CreateProjectFormDTO newProject,
            BindingResult bindingResult,
            Model model,
            Principal principal) {
        createProjectFormValidator.validate(newProject, bindingResult);
        if (bindingResult.hasErrors())
            return "addProject";
        newProject.setAdministratorLogin(principal.getName());
        projectService.createNewProjectFromForm(newProject);
        return String.format("redirect:/projects/%s", newProject.getName());
    }

    @GetMapping("/{projectName}")
    public String showProjectInfo(@PathVariable String projectName) {
        return "project";
    }
}
