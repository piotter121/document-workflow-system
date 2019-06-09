package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CorrectContentTypeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectContentType {
    String message() default "notCorrectContentType";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
