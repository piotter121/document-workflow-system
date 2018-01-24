package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Created by piotr on 31.12.16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(UUID projectId) {
        super(String.format("Nie znaleziono projektu o identyfikatorze równym %s", projectId.toString()));
    }
}
