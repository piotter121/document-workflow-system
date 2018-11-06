package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.CorrectContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.UniqueVersionString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by piotr on 19.01.17.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueVersionString
@CorrectContentType
public class NewVersionForm {
    @NotNull
    @EqualsAndHashCode.Include
    private UUID fileId, taskId, projectId;

    @NotNull
    @ToString.Exclude
    private MultipartFile file;

    @NotNull
    @EqualsAndHashCode.Include
    private String versionString;

    @NotNull
    private String message;
}

