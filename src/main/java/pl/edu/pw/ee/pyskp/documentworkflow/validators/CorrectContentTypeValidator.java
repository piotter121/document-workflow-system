package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CorrectContentTypeValidator implements ConstraintValidator<CorrectContentType, NewVersionForm> {

    @NonNull
    private final FilesMetadataService filesMetadataService;

    @Override
    public void initialize(CorrectContentType constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewVersionForm value, ConstraintValidatorContext context) {
        try (InputStream fileInputStream = value.getFile().getInputStream()) {
            return filesMetadataService.hasContentTypeAs(value.getFileId(), fileInputStream);
        } catch (FileNotFoundException | IOException e) {
            log.error("Exception occurred during checking content type", e);
            return false;
        }
    }

}
