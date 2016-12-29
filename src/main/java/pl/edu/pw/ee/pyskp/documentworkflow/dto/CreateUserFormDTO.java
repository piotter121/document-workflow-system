package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by piotr on 14.12.16.
 */
public class CreateUserFormDTO {

    @Length(min = 5)
    private String login = "";

    @NotEmpty
    @Pattern(regexp = "[a-zA-z]+@[a-zA-z]+\\.[a-zA-Z]+")
    private String email = "";

    private String firstName = "";

    private String lastName = "";

    @Length(min = 6)
    private String password = "";

    @Length(min = 6)
    private String passwordRepeated = "";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
