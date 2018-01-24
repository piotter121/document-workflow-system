package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserSummary;

/**
 * Created by piotr on 01.01.17.
 */
@Data
@EqualsAndHashCode(of = {"login", "email"})
@NoArgsConstructor
@ToString
public class UserInfoDTO {
    private String login;
    private String firstName;
    private String lastName;
    private String email;

    public UserInfoDTO(User user) {
        setLogin(user.getLogin());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
    }

    public UserInfoDTO(UserSummary userSummary) {
        login = userSummary.getLogin();
        firstName = userSummary.getFirstName();
        lastName = userSummary.getLastName();
        email = userSummary.getEmail();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
