package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by piotr on 16.12.16.
 */
@Controller("/projects")
public class ProjectsController {
    @RequestMapping
    public String getUserProjects(Model model) {
        return "projects";
    }
}
