package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateUserFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.service.AuthenticationService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.validator.CreateUserFormValidator;

/**
 * Created by piotr on 20.12.16.
 */
@Controller
public class RegistrationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final CreateUserFormValidator createUserFormValidator;

    public RegistrationController(UserService userService,
                                  AuthenticationService authenticationService,
                                  CreateUserFormValidator createUserFormValidator) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.createUserFormValidator = createUserFormValidator;
    }

    @GetMapping("/register")
    public String getRegistrationForm(@ModelAttribute("newUser") CreateUserFormDTO newUser) {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationOfNewUser(
            @ModelAttribute("newUser") CreateUserFormDTO newUser,
            BindingResult bindingResult) {
        createUserFormValidator.validate(newUser, bindingResult);
        if (bindingResult.hasErrors())
            return "register";
        userService.createUserFromForm(newUser);
        authenticationService.autologin(newUser.getLogin(), newUser.getPassword());
        return "redirect:/projects";
    }

}
