package pl.edu.pw.ee.pyskp.documentworkflow.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.service.FilesMetadataService;

/**
 * Created by piotr on 19.01.17.
 */
@Component
public class NewVersionFormValidator implements Validator {
    private final FilesMetadataService filesMetadataService;

    public NewVersionFormValidator(FilesMetadataService filesMetadataService) {
        this.filesMetadataService = filesMetadataService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewVersionForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewVersionForm form = (NewVersionForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "versionString", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "NotBlank");
        if (!filesMetadataService.isValidVersionStringForFile(form.getVersionString(), form.getFileId())) {
            errors.rejectValue("versionString", "Duplicate.versionForm.versionString");
        }
        if (form.getFile().isEmpty()) {
            errors.rejectValue("file", "NotBlank");
        }
        if (!filesMetadataService.hasContentTypeAs(form.getFileId(), form.getFile())) {
            errors.rejectValue("file", "WrongContentType");
        }
    }
}
