package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.CreateUserFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.services.AuthenticationService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.CreateUserFormValidator;

/**
 * Created by piotr on 20.12.16.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CreateUserFormValidator createUserFormValidator;

    @GetMapping
    public String getRegistrationForm(@ModelAttribute("newUser") CreateUserFormDTO newUser) {
        return "register";
    }

    @PostMapping
    public String processRegistrationOfNewUser(
            @ModelAttribute("newUser") CreateUserFormDTO newUser,
            BindingResult bindingResult) {
        createUserFormValidator.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) return "register";
        userService.createUserFromForm(newUser);
        authenticationService.autologin(newUser.getLogin(), newUser.getPassword());
        return "redirect:/projects";
    }

}
