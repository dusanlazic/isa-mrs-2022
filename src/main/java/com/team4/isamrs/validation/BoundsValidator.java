package com.team4.isamrs.validation;

import com.team4.isamrs.dto.updation.LoyaltyProgramCategoriesUpdationDTO;
import com.team4.isamrs.dto.updation.LoyaltyProgramCategoryUpdationDTO;
import com.team4.isamrs.model.loyalty.TargetedAccountType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BoundsValidator implements ConstraintValidator<Bounds, LoyaltyProgramCategoriesUpdationDTO> {

    @Override
    public void initialize(Bounds constraintAnnotation) { }

    @Override
    public boolean isValid(LoyaltyProgramCategoriesUpdationDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        List<LoyaltyProgramCategoryUpdationDTO> categoryDTOs = dto.getCategories();
        categoryDTOs.sort(Comparator.comparing(LoyaltyProgramCategoryUpdationDTO::getPointsLowerBound));

        List<LoyaltyProgramCategoryUpdationDTO> advertiserCategoryDTOs = new LinkedList<>();
        List<LoyaltyProgramCategoryUpdationDTO> customerCategoryDTOs = new LinkedList<>();

        for (LoyaltyProgramCategoryUpdationDTO d : categoryDTOs) {
            if (d.getTargetedAccountType().equals(TargetedAccountType.ADVERTISER.toString())) {
                advertiserCategoryDTOs.add(d);
            } else {
                customerCategoryDTOs.add(d);
            }
        }

        return boundsAreValid(advertiserCategoryDTOs) && boundsAreValid(customerCategoryDTOs);
    }

    private boolean boundsAreValid(List<LoyaltyProgramCategoryUpdationDTO> dtos) {
        Integer prevUpperBound = 0;
        for (LoyaltyProgramCategoryUpdationDTO dto : dtos) {
            if (prevUpperBound == 0 || prevUpperBound == dto.getPointsLowerBound() - 1) {
                prevUpperBound = dto.getPointsUpperBound();
            } else {
                return false;
            }
        }
        return true;
    }
}
