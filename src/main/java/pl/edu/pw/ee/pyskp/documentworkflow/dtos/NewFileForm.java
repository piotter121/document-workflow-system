package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Created by piotr on 06.01.17.
 */
@NoArgsConstructor
@Data
public class NewFileForm {
    @NotBlank
    @Length(max = 255)
    private String name = "";

    @Length(max = 1024)
    private String description;

    @NotNull
    private MultipartFile file;

    @NotBlank
    @Length(max = 20)
    private String versionString = "1";

    @NotBlank
    @Length(max = 1024)
    private String versionMessage = "Dodanie pliku";

    public NewFileForm(String name, String description, MultipartFile file, String versionString) {
        this.name = name;
        this.description = description;
        this.file = file;
        this.versionString = versionString;
    }
}
