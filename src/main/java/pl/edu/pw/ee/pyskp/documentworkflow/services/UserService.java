package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewUserDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UserNotFoundException;

/**
 * Created by piotr on 14.12.16.
 */
public interface UserService {
    User getUserByEmail(String email) throws UserNotFoundException;

    User getCurrentUser();

    String getCurrentUserEmail();

    void createUserFromForm(NewUserDTO form);
}
