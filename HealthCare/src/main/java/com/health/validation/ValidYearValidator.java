package com.health.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidYearValidator implements ConstraintValidator<ValidYear, Integer> {

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        return year <= LocalDate.now().getYear();
    }
}
