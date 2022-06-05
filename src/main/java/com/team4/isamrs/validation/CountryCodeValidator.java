package com.team4.isamrs.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Locale;

public class CountryCodeValidator implements ConstraintValidator<CountryCode, String> {
    private List<String> countryCodes;

    @Override
    public void initialize(CountryCode constraintAnnotation) {
        this.countryCodes = List.of(Locale.getISOCountries());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() != 2)
            return false;

        return this.countryCodes.contains(s.toUpperCase());
    }
}
