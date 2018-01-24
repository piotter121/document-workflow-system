package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by piotr on 06.01.17.
 */
@ResponseStatus(NOT_FOUND)
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(UUID id) {
        super(String.format("File with ID = %s has not been found", id.toString()));
    }
}
