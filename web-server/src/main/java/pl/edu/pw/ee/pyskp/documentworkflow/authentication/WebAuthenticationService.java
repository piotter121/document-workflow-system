package pl.edu.pw.ee.pyskp.documentworkflow.authentication;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class WebAuthenticationService {
    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final UserService userService;

    @NonNull
    private final TokenHandler tokenHandler;

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
