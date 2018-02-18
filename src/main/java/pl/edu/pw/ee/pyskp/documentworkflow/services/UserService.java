package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.CreateUserFormDTO;

/**
 * Created by piotr on 14.12.16.
 */
public interface UserService extends UserDetailsService {
    User getUserByEmail(String email);

    User getCurrentUser();

    String getCurrentUserLogin();

    void createUserFromForm(CreateUserFormDTO form);
}
