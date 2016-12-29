package pl.edu.pw.ee.pyskp.documentworkflow.exception;

/**
 * Created by piotr on 29.12.16.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String nonExistingUserLogin) {
        super(String.format("Nie znaleziono użytkownika o nazwie %s", nonExistingUserLogin));
    }
}
