package com.example.demo.validators;

import com.example.demo.annotation.ValidEmailAddress;

import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailValidator implements ConstraintValidator<ValidEmailAddress, String> {

    private ValidEmailAddress validEmailAddress;

    @Override
    public void initialize(ValidEmailAddress constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        validEmailAddress = constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (validEmailAddress.nullable() && s == null)
            return true;
        return isValidEmailAddress(s);
    }

    public static boolean isValidEmailAddress(String email) {
        boolean isValid = false;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();

            // check that username in email  string contains at least on letter
            String emailPart0 = email.split("@")[0];
            isValid = emailPart0.matches(".*[a-zA-Z]+.*");
        } catch (Exception ignored) {
        }

        return isValid;
    }
}
