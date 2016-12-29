package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateUserFormDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 14.12.16.
 */
public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByLogin(String login);

    List<User> getAllUsers();

    User createUserFromForm(CreateUserFormDTO form);
}
