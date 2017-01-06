package pl.edu.pw.ee.pyskp.documentworkflow.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by piotr on 06.01.17.
 */
@ResponseStatus(NOT_FOUND)
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(long id) {
        super(String.format("File with ID = %d has not been found", id));
    }
}
