package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import org.hibernate.validator.constraints.NotEmpty;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Principals;

import javax.validation.constraints.NotNull;

/**
 * Created by piotr on 14.12.16.
 */
public class CreateUserForm {
    @NotEmpty
    private String login = "";

    @NotEmpty
    private String email = "";

    @NotEmpty
    private String password = "";

    @NotEmpty
    private String passwordRepeated = "";

    @NotNull
    private Principals role = Principals.USER;

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

    public Principals getRole() {
        return role;
    }

    public void setRole(Principals role) {
        this.role = role;
    }
}
