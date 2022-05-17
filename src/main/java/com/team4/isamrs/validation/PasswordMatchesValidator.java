package com.team4.isamrs.validation;

import com.team4.isamrs.dto.creation.AdminCreationDTO;
import com.team4.isamrs.dto.creation.CustomerCreationDTO;
import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;
import com.team4.isamrs.dto.updation.PasswordUpdationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        if (obj instanceof RegistrationRequestCreationDTO dto) {
            return dto.getPassword().equals(dto.getPasswordConfirmation());
        }
        else if (obj instanceof CustomerCreationDTO dto) {
            return dto.getPassword().equals(dto.getPasswordConfirmation());
        }
        else if (obj instanceof AdminCreationDTO dto) {
            return dto.getPassword().equals(dto.getPasswordConfirmation());
        }
        else if (obj instanceof PasswordUpdationDTO dto) {
            return dto.getPassword().equals(dto.getPasswordConfirmation());
        }
        return false;
    }
}