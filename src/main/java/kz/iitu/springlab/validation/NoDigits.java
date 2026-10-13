package kz.iitu.springlab.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NoDigitsValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NoDigits {

    String message() default "{validation.noDigits}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}