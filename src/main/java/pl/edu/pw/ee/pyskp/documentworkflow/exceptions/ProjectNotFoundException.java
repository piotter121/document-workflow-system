package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import java.util.Collections;
import java.util.Map;

/**
 * Created by piotr on 31.12.16.
 */
public class ProjectNotFoundException extends ResourceNotFoundException {
    private final String projectId;

    public ProjectNotFoundException(String projectId) {
        super(String.format("Project with id = '%s' has not been found", projectId));
        this.projectId = projectId;
    }

    @Override
    public Map<String, String> getMessageParams() {
        return Collections.singletonMap("projectId", projectId);
    }
}
