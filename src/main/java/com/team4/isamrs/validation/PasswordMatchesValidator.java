package com.team4.isamrs.validation;

import com.team4.isamrs.dto.creation.AdminCreationDTO;
import com.team4.isamrs.dto.creation.CustomerCreationDTO;
import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;
import com.team4.isamrs.dto.updation.InitialPasswordUpdationDTO;
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
            return passwordsMatch(dto.getPassword(), dto.getPasswordConfirmation());
        }
        else if (obj instanceof CustomerCreationDTO dto) {
            return passwordsMatch(dto.getPassword(), dto.getPasswordConfirmation());
        }
        else if (obj instanceof AdminCreationDTO dto) {
            return passwordsMatch(dto.getPassword(), dto.getPasswordConfirmation());
        }
        else if (obj instanceof InitialPasswordUpdationDTO dto) {
            return passwordsMatch(dto.getPassword(), dto.getPasswordConfirmation());
        }
        else if (obj instanceof PasswordUpdationDTO dto) {
            return passwordsMatch(dto.getNewPassword(), dto.getPasswordConfirmation());
        }
        return false;
    }

    private boolean passwordsMatch(String password, String confirmation) {
        if (password == null || confirmation == null)
            return false;
        return password.equals(confirmation);
    }
}