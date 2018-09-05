package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

/**
 * Created by piotr on 01.01.17.
 */
@Data
public class UserInfoDTO {
    public static UserInfoDTO fromUser(User user) {
        return new UserInfoDTO(user.getFirstName(), user.getLastName(), user.getFullName(), user.getEmail());
    }

    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String email;
}
