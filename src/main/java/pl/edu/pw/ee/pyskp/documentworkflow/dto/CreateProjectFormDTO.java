package pl.edu.pw.ee.pyskp.documentworkflow.dto;

/**
 * Created by piotr on 29.12.16.
 */
public class CreateProjectFormDTO {
    private String name;
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
