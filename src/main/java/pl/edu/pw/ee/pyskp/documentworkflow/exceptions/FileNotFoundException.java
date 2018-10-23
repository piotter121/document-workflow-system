package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import java.util.Collections;
import java.util.Map;

/**
 * Created by piotr on 06.01.17.
 */
public class FileNotFoundException extends ResourceNotFoundException {
    private final String fileId;

    public FileNotFoundException(String fileId) {
        super(String.format("File with ID = %s has not been found", fileId));
        this.fileId = fileId;
    }

    @Override
    public Map<String, String> getMessageParams() {
        return Collections.singletonMap("fileId", fileId);
    }
}
