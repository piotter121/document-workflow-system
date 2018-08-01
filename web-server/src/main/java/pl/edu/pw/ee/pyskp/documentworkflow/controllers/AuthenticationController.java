package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.authentication.WebAuthenticationService;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewUserDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AuthenticationController {
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    @NonNull
    private final WebAuthenticationService authenticationService;

    @NonNull
    private final UserService userService;

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getToken(@RequestParam String email, @RequestParam String password)
            throws UserNotFoundException {
        try {
            String token = authenticationService.getToken(email, password);

            if (StringUtils.isNotBlank(token)) {
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            }
        } catch (UserNotFoundException e) {
            logger.warn("User not found exception", e);
            throw e;
        } catch (Exception e) {
            logger.warn("Exception during get token", e);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public void registerNewUser(@Valid @RequestBody NewUserDTO createUser) {
        userService.createUserFromForm(createUser);
    }
}
