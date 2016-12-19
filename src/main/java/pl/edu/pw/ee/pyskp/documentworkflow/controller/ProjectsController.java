package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by piotr on 16.12.16.
 */
@Controller
public class ProjectsController {
    @RequestMapping("/projects")
    public String getUserProjects(Model model) {
        return "projects";
    }
}
