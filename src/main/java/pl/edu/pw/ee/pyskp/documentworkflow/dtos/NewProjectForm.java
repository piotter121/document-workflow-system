package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Created by piotr on 29.12.16.
 */
@Data
public class NewProjectForm {
    @NotBlank
    @Length(max = 40)
    private String name;

    @Length(max = 1024)
    private String description;
}
