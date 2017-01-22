package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Created by piotr on 06.01.17.
 */
public class NewFileForm {
    @NotBlank
    @Length(min = 5, max = 255)
    private String name = "";

    @Length(max = 1024)
    private String description;

    @NotNull
    private MultipartFile file;

    @NotBlank
    @Length(min = 1, max = 20)
    private String versionString = "1";

    @NotBlank
    @Length(min = 1, max = 1024)
    private String versionMessage = "Dodanie pliku";

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }

    public String getVersionMessage() {
        return versionMessage;
    }

    public void setVersionMessage(String versionMessage) {
        this.versionMessage = versionMessage;
    }
}
