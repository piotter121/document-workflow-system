package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.CreateUserFormDTO;

import java.util.Optional;

/**
 * Created by piotr on 14.12.16.
 */
public interface UserService {

    Optional<User> getUserByLogin(String login);

    User getUserByEmail(String email);

    User getCurrentUser();

    String getCurrentUserLogin();

    void createUserFromForm(CreateUserFormDTO form);

}
