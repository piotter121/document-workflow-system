package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by piotr on 29.12.16.
 */
public class NewProjectForm {
    @NotBlank
    @Length(min = 5, max = 40)
    private String name;

    @Length(max = 1024)
    private String description;

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
}
