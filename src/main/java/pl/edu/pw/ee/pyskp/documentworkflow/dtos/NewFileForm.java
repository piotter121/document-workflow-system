package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Created by piotr on 06.01.17.
 */
@Data
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
}
