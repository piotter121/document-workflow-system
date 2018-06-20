package pl.edu.pw.ee.pyskp.documentworkflow.authentication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

@Service
public class WebAuthenticationService {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final TokenHandler tokenHandler;

    @Autowired
    public WebAuthenticationService(PasswordEncoder passwordEncoder, UserService userService, TokenHandler tokenHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.tokenHandler = tokenHandler;
    }

    public String getToken(String email, String password) throws UserNotFoundException {
        if (StringUtils.isAnyBlank(email, password))
            return null;

        User user = userService.getUserByEmail(email);
        if (passwordEncoder.matches(password, user.getPassword()))
            return tokenHandler.createTokenForUser(new UserAuthentication(user));
        else
            return null;
    }
}
