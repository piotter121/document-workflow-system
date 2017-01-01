package pl.edu.pw.ee.pyskp.documentworkflow.dto;

/**
 * Created by piotr on 01.01.17.
 */
public class UserInfoDTO {
    private String login;
    private String firstName;
    private String lastName;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return firstName + " " + lastName;
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
