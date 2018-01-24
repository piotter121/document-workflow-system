package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Created by piotr on 19.01.17.
 */
@Data
public class NewVersionForm {
    private UUID fileId, taskId, projectId;
    private MultipartFile file;
    private String versionString;
    private String message;
}

