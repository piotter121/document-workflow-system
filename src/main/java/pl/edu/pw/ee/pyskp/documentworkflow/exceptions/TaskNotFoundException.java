package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import java.util.Collections;
import java.util.Map;

/**
 * Created by p.pysk on 02.01.2017.
 */
public class TaskNotFoundException extends ResourceNotFoundException {
    private final String taskId;

    public TaskNotFoundException(String taskId) {
        super(String.format("Task with id = '%s' has not been found", taskId));
        this.taskId = taskId;
    }

    @Override
    public Map<String, String> getMessageParams() {
        return Collections.singletonMap("taskId", taskId);
    }
}
