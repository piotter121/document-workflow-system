package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserSummary;

/**
 * Created by piotr on 01.01.17.
 */
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "email")
public class UserInfoDTO {
    private String firstName;
    private String lastName;
    private String email;

    public UserInfoDTO(UserSummary userSummary) {
        firstName = userSummary.getFirstName();
        lastName = userSummary.getLastName();
        email = userSummary.getEmail();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
