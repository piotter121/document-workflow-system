package pl.edu.pw.ee.pyskp.documentworkflow.exception;

/**
 * Created by p.pysk on 04.01.2017.
 */
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
