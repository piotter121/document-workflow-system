package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CorrectContentTypeValidator implements ConstraintValidator<CorrectContentType, NewVersionForm> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorrectContentTypeValidator.class);

    @NonNull
    private final FilesMetadataService filesMetadataService;

    @Override
    public void initialize(CorrectContentType constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewVersionForm value, ConstraintValidatorContext context) {
        try {
            return filesMetadataService.hasContentTypeAs(value.getTaskId(), value.getFileId(), value.getFile().getBytes());
        } catch (FileNotFoundException | IOException e) {
            LOGGER.error("Exception occurred during checking content type", e);
            return false;
        }
    }

}
