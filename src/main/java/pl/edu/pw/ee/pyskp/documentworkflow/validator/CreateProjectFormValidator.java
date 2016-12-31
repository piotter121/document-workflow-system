package pl.edu.pw.ee.pyskp.documentworkflow.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateProjectFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;

/**
 * Created by piotr on 29.12.16.
 */
@Component
public class CreateProjectFormValidator implements Validator {
    private final ProjectService projectService;

    public CreateProjectFormValidator(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateProjectFormDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateProjectFormDTO form = (CreateProjectFormDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        String name = form.getName();
        if (projectService.existsProjectWithName(name))
            errors.rejectValue("name", "Duplicate.projectForm.name");
        int nameLength = name.length();
        if (nameLength < 5 || nameLength > 32)
            errors.rejectValue("name", "Size.projectForm.name");
        if (form.getDescription().length() > 1024)
            errors.rejectValue("description", "Size.projectForm.description");
    }
}
