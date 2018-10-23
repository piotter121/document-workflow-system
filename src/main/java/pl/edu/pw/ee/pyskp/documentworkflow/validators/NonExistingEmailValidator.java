package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class NonExistingEmailValidator implements ConstraintValidator<NonExistingEmail, String> {
    @NonNull
    private final UserRepository userRepository;

    @Override
    public void initialize(NonExistingEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.findOneByEmail(email).isPresent();
    }

}
