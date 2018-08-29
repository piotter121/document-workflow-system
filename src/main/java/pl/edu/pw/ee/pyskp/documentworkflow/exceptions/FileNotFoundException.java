package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
public class FileNotFoundException extends ResourceNotFoundException {
    private final String fileId;

    public FileNotFoundException(UUID id) {
        super(String.format("File with ID = %s has not been found", id.toString()));
        fileId = id.toString();
    }

    @Override
    public Map<String, String> getMessageParams() {
        return Collections.singletonMap("fileId", fileId);
    }
}
