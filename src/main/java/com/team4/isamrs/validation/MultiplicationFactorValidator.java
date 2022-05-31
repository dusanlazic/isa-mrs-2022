package com.team4.isamrs.validation;

import com.team4.isamrs.dto.updation.LoyaltyProgramCategoryUpdationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiplicationFactorValidator implements ConstraintValidator<MultiplicationFactor, LoyaltyProgramCategoryUpdationDTO> {

    @Override
    public void initialize(MultiplicationFactor constraintAnnotation) { }

    @Override
    public boolean isValid(LoyaltyProgramCategoryUpdationDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        if (dto.getTargetedAccountType().equals("ADVERTISER"))
            return dto.getMultiply().compareTo(BigDecimal.ONE) >= 0;
        if (dto.getTargetedAccountType().equals("CUSTOMER"))
            return dto.getMultiply().compareTo(BigDecimal.ONE) <= 0 && dto.getMultiply().compareTo(BigDecimal.ZERO) > 0;

        return false;
    }
}
