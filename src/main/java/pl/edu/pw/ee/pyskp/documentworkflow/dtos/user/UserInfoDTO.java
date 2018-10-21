package pl.edu.pw.ee.pyskp.documentworkflow.dtos.user;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserSummary;

/**
 * Created by piotr on 01.01.17.
 */
@Value
public class UserInfoDTO {
    @NonNull
    String firstName, lastName, email;

    public static UserInfoDTO fromUserSummary(UserSummary userSummary) {
        return new UserInfoDTO(userSummary.getFirstName(), userSummary.getLastName(), userSummary.getEmail());
    }

    @SuppressWarnings("unused")
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
