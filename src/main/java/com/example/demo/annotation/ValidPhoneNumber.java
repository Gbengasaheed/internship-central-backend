package com.example.demo.annotation;

import com.example.demo.validators.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({TYPE, PARAMETER, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidPhoneNumber {
    String message() default "phone_num.invalid";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
