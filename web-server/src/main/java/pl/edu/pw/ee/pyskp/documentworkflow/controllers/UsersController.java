package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/api/user")
public class UsersController {

    @NonNull
    private UserService userService;

    @GetMapping(path = "/exists")
    public boolean checkIfUserExists(@RequestParam String email) {
        return userService.checkIfUserExists(email);
    }

}
