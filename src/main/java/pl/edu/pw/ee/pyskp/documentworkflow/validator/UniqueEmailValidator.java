package pl.edu.pw.ee.pyskp.documentworkflow.validator;

import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by p.pysk on 02.01.2017.
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.getUserByEmail(value).isPresent();
    }
}
