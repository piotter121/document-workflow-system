package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NonExistingEmailValidator implements ConstraintValidator<NonExistingEmail, String> {
    private final UserRepository userRepository;

    @Autowired
    public NonExistingEmailValidator(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public void initialize(NonExistingEmail constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.findOneByEmail(email).isPresent();
    }

}
