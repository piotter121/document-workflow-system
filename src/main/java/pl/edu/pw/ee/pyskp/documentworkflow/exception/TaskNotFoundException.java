package pl.edu.pw.ee.pyskp.documentworkflow.exception;

/**
 * Created by p.pysk on 02.01.2017.
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long id) {
        super("Nie znaleziono zadania o id = " + id);
    }
}
