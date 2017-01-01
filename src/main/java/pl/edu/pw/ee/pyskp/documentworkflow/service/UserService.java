package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateUserFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 14.12.16.
 */
public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByLogin(String login);

    User getCurrentUser() throws UserNotFoundException;

    List<User> getAllUsers();

    User createUserFromForm(CreateUserFormDTO form);

    static UserInfoDTO mapToUserInfoDTO(User user) {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setLogin(user.getLogin());
        dto.setFirstName(user.getPersonalData().getFirstName());
        dto.setLastName(user.getPersonalData().getLastName());
        return dto;
    }
}
