package com.example.demo.validators;

import com.example.demo.annotation.ValidPhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Configurable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Configurable
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private PhoneNumberUtil phoneNumberUtil;
    private ValidPhoneNumber validPhoneNumber;

    public static boolean isPhoneNumberValid(String s) {
        if (s != null && !s.contains(" ")) {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number;
            try {
                number = phoneNumberUtil.parse(s, Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
                return phoneNumberUtil.isPossibleNumber(number);
            } catch (NumberParseException ignored) {
            }
        }
        return false;
    }

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        phoneNumberUtil = PhoneNumberUtil.getInstance();
        this.validPhoneNumber = constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (validPhoneNumber.nullable() && s == null)
            return true;

        if (s != null && !s.contains(" ")) {
            try {
                Phonenumber.PhoneNumber number = phoneNumberUtil.parse(s, Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
                return phoneNumberUtil.isPossibleNumber(number);
            } catch (NumberParseException ignored) {
            }
        }
        return false;
    }
}
