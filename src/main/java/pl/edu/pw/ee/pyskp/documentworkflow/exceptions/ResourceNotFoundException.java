package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public abstract class ResourceNotFoundException extends Exception {
    protected ResourceNotFoundException(String message) {
        super(message);
    }

    public abstract Map<String, String> getMessageParams();
}
