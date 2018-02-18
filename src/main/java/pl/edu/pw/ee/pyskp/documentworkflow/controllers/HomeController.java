package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by piotr on 16.12.16.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    @SuppressWarnings("SameReturnValue")
    public String home() {
        return "redirect:/projects";
    }
}
