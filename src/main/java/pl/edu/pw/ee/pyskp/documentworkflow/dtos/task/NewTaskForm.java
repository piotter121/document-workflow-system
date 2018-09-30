package pl.edu.pw.ee.pyskp.documentworkflow.dtos.task;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.ExistingUserEmail;

/**
 * Created by p.pysk on 02.01.2017.
 */
@Data
public class NewTaskForm {
    @NotBlank
    @Length(max = 50)
    private String name;

    @Length(max = 1024)
    private String description;

    @NotBlank
    @ExistingUserEmail
    private String administratorEmail;
}
