package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by piotr on 16.12.16.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/**/{path:[^.]+}")
    public String redirect() {
        // Forward to home page so that route is preserved.
        return "forward:/";
    }
}
