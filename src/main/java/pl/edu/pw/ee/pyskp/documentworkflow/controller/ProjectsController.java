package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by piotr on 16.12.16.
 */
@Controller
public class ProjectsController {
    @GetMapping("/projects")
    public String getUserProjects() {
        return "projects";
    }
}
