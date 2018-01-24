package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Created by p.pysk on 02.01.2017.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(UUID id) {
        super(String.format("Nie znaleziono zadania o id = %s", id));
    }
}
