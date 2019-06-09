package pl.edu.pw.ee.pyskp.documentworkflow.validators;

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
 * Created by p.pysk on 02.01.2017.
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Constraint(validatedBy = ExistingUserEmailValidator.class)
public @interface ExistingUserEmail {
    String message() default "UserDoesNotExists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
