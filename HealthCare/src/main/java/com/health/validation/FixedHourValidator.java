package com.health.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class FixedHourValidator implements ConstraintValidator<ValidFixedHours, LocalTime> {

    @Override
    public boolean isValid(LocalTime localTime, ConstraintValidatorContext constraintValidatorContext) {
        return localTime.getMinute() == 0 && localTime.getSecond() == 0;
    }
}
