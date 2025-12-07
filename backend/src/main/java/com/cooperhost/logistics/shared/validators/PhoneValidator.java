package com.cooperhost.logistics.shared.validators;

import com.cooperhost.logistics.shared.interfaces.Phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        return value.matches("^\\+[0-9]{11,12}$");
    }
}