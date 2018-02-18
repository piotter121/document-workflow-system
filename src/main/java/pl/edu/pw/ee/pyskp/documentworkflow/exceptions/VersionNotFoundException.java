package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by p.pysk on 16.01.2017.
 */
@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VersionNotFoundException extends RuntimeException {
    public VersionNotFoundException(long versionId) {
        super(String.format("Version with id = %d could not be found", versionId));
    }
}
