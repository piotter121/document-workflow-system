package pl.edu.pw.ee.pyskp.documentworkflow.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by p.pysk on 02.01.2017.
 */
public class ExistingUserEmailValidator implements ConstraintValidator<ExistingUserEmail, String> {
    private final UserService userService;

    @Autowired
    public ExistingUserEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(ExistingUserEmail existingUserEmail) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.getUserByEmail(value).isPresent();
    }
}
