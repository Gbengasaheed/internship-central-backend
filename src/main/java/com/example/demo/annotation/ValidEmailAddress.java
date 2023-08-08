package com.example.demo.annotation;

import com.example.demo.validators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Email(message = "email.invalid_email")
@Constraint(validatedBy = EmailValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidEmailAddress {
    String message() default "email.invalid_email";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
