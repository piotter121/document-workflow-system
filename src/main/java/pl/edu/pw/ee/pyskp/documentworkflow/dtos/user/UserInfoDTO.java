package pl.edu.pw.ee.pyskp.documentworkflow.dtos.user;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserSummary;

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
    @EqualsAndHashCode.Include
    @ToString.Include
    String email;

    public static UserInfoDTO fromUserSummary(UserSummary userSummary) {
        return new UserInfoDTO(userSummary.getFirstName(), userSummary.getLastName(), userSummary.getEmail());
    }

    @SuppressWarnings("unused")
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
