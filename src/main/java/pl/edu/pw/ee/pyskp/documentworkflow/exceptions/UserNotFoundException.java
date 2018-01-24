package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by piotr on 29.12.16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String loginOrEmail) {
        super(String.format("Nie znaleziono użytkownika z podaną nazwą lub adresem e-mail: %s", loginOrEmail));
    }
}
