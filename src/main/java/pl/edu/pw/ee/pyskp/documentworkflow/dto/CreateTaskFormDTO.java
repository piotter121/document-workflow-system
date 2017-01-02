package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import pl.edu.pw.ee.pyskp.documentworkflow.validator.ExistingUserEmail;

/**
 * Created by p.pysk on 02.01.2017.
 */
public class CreateTaskFormDTO {
    @NotEmpty(message = "NotEmpty")
    @Length(min = 5, max = 50, message = "Size.taskForm.name")
    private String name;

    @Length(max = 1024, message = "Size.taskForm.description")
    private String description;

    @NotEmpty(message = "NotEmpty")
    @Email(message = "Pattern.taskForm.administratorEmail")
    @ExistingUserEmail
    private String administratorEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdministratorEmail() {
        return administratorEmail;
    }

    public void setAdministratorEmail(String administratorEmail) {
        this.administratorEmail = administratorEmail;
    }
}
