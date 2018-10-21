package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UniqueVersionStringValidator implements ConstraintValidator<UniqueVersionString, NewVersionForm> {

    @NonNull
    private final VersionService versionService;

    @Override
    public void initialize(UniqueVersionString constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewVersionForm value, ConstraintValidatorContext context) {
        return StringUtils.isNotBlank(value.getVersionString())
                && !versionService.existsByVersionString(value.getFileId(), value.getVersionString());
    }
}
