package pl.edu.pw.ee.pyskp.documentworkflow.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("unused")
@Constraint(validatedBy = UniqueVersionStringValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueVersionString {
    String message() default "nonUniqueVersionString";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
