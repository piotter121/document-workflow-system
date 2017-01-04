package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateUserFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UserNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by piotr on 14.12.16.
 */
public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByLogin(String login);

    Optional<User> getUserByEmail(String email);

    User getCurrentUser() throws UserNotFoundException;

    User createUserFromForm(CreateUserFormDTO form);

    static UserInfoDTO mapToUserInfoDTO(User user) {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getPersonalData().getFirstName());
        dto.setLastName(user.getPersonalData().getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    static List<UserInfoDTO> mapAllToUserInfoDTO(Collection<User> users) {
        return users != null
                ? users.stream().map(UserService::mapToUserInfoDTO).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
