package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by piotr on 06.01.17.
 */
@Data
public class NewFileForm {
    @NotBlank
    @Length(max = 255)
    private final String name;

    @Length(max = 1024)
    private final String description;

    @NotNull
    private final MultipartFile file;

    @NotBlank
    @Length(max = 20)
    private final String versionString;
}
