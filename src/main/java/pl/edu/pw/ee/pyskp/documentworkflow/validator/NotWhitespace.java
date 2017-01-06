package pl.edu.pw.ee.pyskp.documentworkflow.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by piotr on 06.01.17.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotWhitespaceValidator.class)
@Documented
public @interface NotWhitespace {
    String message() default "{NotWhitespace}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
