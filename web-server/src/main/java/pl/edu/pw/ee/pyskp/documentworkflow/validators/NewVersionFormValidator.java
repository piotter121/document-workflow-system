package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;

/**
 * Created by piotr on 19.01.17.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class NewVersionFormValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewVersionFormValidator.class);

    @NonNull
    private final FilesMetadataService filesMetadataService;

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
        try {
            if (!filesMetadataService.hasContentTypeAs(form.getTaskId(), form.getFileId(), form.getFile())) {
                errors.rejectValue("file", "WrongContentType");
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found during new version validation", e);
            errors.rejectValue("file", "FileNotFound");
        }
    }
}
