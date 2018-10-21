package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by p.pysk on 02.01.2017.
 */
@RequiredArgsConstructor
public class ExistingUserEmailValidator implements ConstraintValidator<ExistingUserEmail, String> {
    @NonNull
    private final UserRepository userRepository;

    @Override
    public void initialize(ExistingUserEmail existingUserEmail) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findById(value).isPresent();
    }
}
