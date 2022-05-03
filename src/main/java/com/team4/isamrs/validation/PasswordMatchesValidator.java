package com.team4.isamrs.validation;

import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistrationRequestCreationDTO registrationRequest = (RegistrationRequestCreationDTO) obj;
        return registrationRequest.getPassword().equals(registrationRequest.getPasswordConfirmation());
    }
}