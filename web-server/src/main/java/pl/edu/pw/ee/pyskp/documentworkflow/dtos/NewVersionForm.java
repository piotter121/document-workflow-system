package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.CorrectContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.UniqueVersionString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by piotr on 19.01.17.
 */
@Data
@UniqueVersionString
@CorrectContentType
public class NewVersionForm {
    @NotNull
    private UUID fileId, taskId, projectId;

    @NotNull
    private MultipartFile file;

    @NotNull
    private String versionString;

    @NotNull
    private String message;
}

