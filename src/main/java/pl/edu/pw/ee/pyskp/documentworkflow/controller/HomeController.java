package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by piotr on 16.12.16.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/projects";
    }
}
