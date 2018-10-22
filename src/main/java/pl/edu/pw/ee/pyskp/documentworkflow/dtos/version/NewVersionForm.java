package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.CorrectContentType;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.UniqueVersionString;

import javax.validation.constraints.NotNull;

/**
 * Created by piotr on 19.01.17.
 */
@Data
@UniqueVersionString
@CorrectContentType
public class NewVersionForm {
    @NotNull
    private ObjectId fileId, taskId, projectId;

    @NotNull
    private MultipartFile file;

    @NotNull
    private String versionString;

    @NotNull
    private String message;
}

