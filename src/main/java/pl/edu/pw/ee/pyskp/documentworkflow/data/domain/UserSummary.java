package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.UserDefinedType;

@Data
@EqualsAndHashCode(of = "login")
@NoArgsConstructor
@UserDefinedType("user_summary")
public class UserSummary {
    private String login;

    private String email;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    public UserSummary(User user) {
        login = user.getLogin();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
