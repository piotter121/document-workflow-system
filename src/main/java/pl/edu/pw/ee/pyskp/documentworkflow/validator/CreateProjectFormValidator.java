package pl.edu.pw.ee.pyskp.documentworkflow.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewProjectForm;

/**
 * Created by piotr on 29.12.16.
 */
@Component
public class CreateProjectFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NewProjectForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewProjectForm form = (NewProjectForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        int nameLength = form.getName().length();
        if (nameLength < 5 || nameLength > 40)
            errors.rejectValue("name", "Size.projectForm.name");
        if (form.getDescription().length() > 1024)
            errors.rejectValue("description", "Size.projectForm.description");
    }
}
