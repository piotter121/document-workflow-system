package pl.edu.pw.ee.pyskp.documentworkflow.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateUserFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

/**
 * Created by piotr on 20.12.16.
 */
@Component
public class CreateUserFormValidator implements Validator {

    private final UserService userService;

    public CreateUserFormValidator(UserService userService) {
        this.userService = userService;
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
        String login = form.getLogin();
        int loginLength = login.length();
        if (loginLength < 5 || loginLength > 32) {
            errors.rejectValue("login", "Size.userForm.username");
        }
        if (userService.getUserByLogin(login).isPresent()) {
            errors.rejectValue("login", "Duplicate.userForm.username");
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
