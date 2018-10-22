package pl.edu.pw.ee.pyskp.documentworkflow.dtos.user;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

/**
 * Created by piotr on 01.01.17.
 */
@Value
public class UserInfoDTO {
    @NonNull
    String firstName, lastName, email;

    public static UserInfoDTO fromUser(User user) {
        return new UserInfoDTO(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @SuppressWarnings("unused")
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
