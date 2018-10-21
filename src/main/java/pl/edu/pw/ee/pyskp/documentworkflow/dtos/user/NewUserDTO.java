package pl.edu.pw.ee.pyskp.documentworkflow.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.NonExistingEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by piotr on 14.12.16.
 */
@Data
@NoArgsConstructor
public class NewUserDTO {

    @NotBlank
    @Email
    @NonExistingEmail
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Length(min = 6)
    private String password;

}
