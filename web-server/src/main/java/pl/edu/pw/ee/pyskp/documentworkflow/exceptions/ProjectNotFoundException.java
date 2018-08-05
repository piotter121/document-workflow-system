package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * Created by piotr on 31.12.16.
 */
public class ProjectNotFoundException extends ResourceNotFoundException {
    private final String projectId;

    public ProjectNotFoundException(UUID projectId) {
        super(String.format("Project with id = '%s' has not been found", projectId.toString()));
        this.projectId = projectId.toString();
    }

    @Override
    public Map<String, String> getMessageParams() {
        return Collections.singletonMap("projectId", projectId);
    }
}
