package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import lombok.Getter;

/**
 * Created by piotr on 29.12.16.
 */
public class UserNotFoundException extends Exception {
    @Getter
    private final String email;

    public UserNotFoundException(String email) {
        super(String.format("Nie znaleziono użytkownika z podanym adresem e-mail: %s", email));
        this.email = email;
    }
}
