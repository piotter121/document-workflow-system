package pl.edu.pw.ee.pyskp.documentworkflow.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by piotr on 06.01.17.
 */
public class NotWhitespaceValidator implements ConstraintValidator<NotWhitespace, String> {

    @Override
    public void initialize(NotWhitespace constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
