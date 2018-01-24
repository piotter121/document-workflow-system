package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.services.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by piotr on 16.12.16.
 */
@Controller
@RequestMapping("/projects")
public class ProjectsController {
    private static final Logger logger = Logger.getLogger(ProjectsController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUserProjects(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", new UserInfoDTO(currentUser));
        model.addAttribute("projects",
                projectService.getUserParticipatedProjects(currentUser.getLogin()));
        return "projects";
    }

    private void addCurrentUserToModel(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", new UserInfoDTO(currentUser));
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
        UUID createdProjectId = projectService.createNewProjectFromForm(newProject);
        return String.format("redirect:/projects/%s", createdProjectId.toString());
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("@securityService.hasAccessToProject(#projectId)")
    public String showProjectInfo(@PathVariable UUID projectId,
                                  Model model,
                                  @RequestParam(required = false) String deleted) {
        ProjectInfoDTO project = projectService.getProjectInfo(projectId);

        addCurrentUserToModel(model);
        model.addAttribute("project", project);
        if (deleted != null) model.addAttribute("delete", "success");
        return "project";
    }

    @DeleteMapping("/{projectId}")
    @PreAuthorize("@securityService.isCurrentUserProjectAdministrator(#projectId)")
    public String deleteProject(@PathVariable UUID projectId) {
        logger.debug("Received HTTP DELETE request for deletion project of id=" + projectId);
        projectService.deleteProject(projectId);
        return "redirect:/";
    }
}
