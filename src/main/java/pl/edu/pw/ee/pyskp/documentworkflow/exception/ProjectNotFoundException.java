package pl.edu.pw.ee.pyskp.documentworkflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by piotr on 31.12.16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String nameOfNonExistingProject) {
        super(String.format("Nie znaleziono projektu o nazwie %s", nameOfNonExistingProject));
    }
}
