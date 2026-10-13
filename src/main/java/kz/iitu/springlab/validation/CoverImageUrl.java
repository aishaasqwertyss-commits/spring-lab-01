package kz.iitu.springlab.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = CoverImageUrlValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface CoverImageUrl {

    String message() default "{book.cover.url.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}