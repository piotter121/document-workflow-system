package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.UserRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.CreateUserFormDTO;

import java.util.Objects;

/**
 * Created by piotr on 20.12.16.
 */
@Component
public class CreateUserFormValidator implements Validator {
    private final UserRepository userRepository;

    @Autowired
    public CreateUserFormValidator(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserFormDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateUserFormDTO form = (CreateUserFormDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        String login = form.getLogin();
        int loginLength = login.length();
        if (loginLength < 5 || loginLength > 32) {
            errors.rejectValue("login", "Size.userForm.username");
        }
        if (userRepository.findOneByLogin(login).isPresent()) {
            errors.rejectValue("login", "Duplicate.userForm.username");
        }
        if (userRepository.findOneByEmail(form.getEmail()).isPresent()) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        String password = form.getPassword();
        if (password.length() < 6) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        if (!form.getPasswordRepeated().equals(password)) {
            errors.rejectValue("passwordRepeated", "Diff.userForm.passwordConfirm");
        }
    }
}
