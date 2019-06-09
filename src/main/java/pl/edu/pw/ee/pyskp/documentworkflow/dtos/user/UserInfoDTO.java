package pl.edu.pw.ee.pyskp.documentworkflow.dtos.user;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

/**
 * Created by piotr on 01.01.17.
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class UserInfoDTO {
    @NonNull
    String firstName;

    @NonNull
    String lastName;

    @NonNull
    @ToString.Include
    @EqualsAndHashCode.Include
    String email;

    public static UserInfoDTO fromUser(User user) {
        return new UserInfoDTO(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
