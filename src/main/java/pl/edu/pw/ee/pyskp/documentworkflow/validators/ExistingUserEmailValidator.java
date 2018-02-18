package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by p.pysk on 02.01.2017.
 */
public class ExistingUserEmailValidator implements ConstraintValidator<ExistingUserEmail, String> {
    private final UserRepository userRepository;

    @Autowired
    public ExistingUserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ExistingUserEmail existingUserEmail) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findOneByEmail(value).isPresent();
    }
}
