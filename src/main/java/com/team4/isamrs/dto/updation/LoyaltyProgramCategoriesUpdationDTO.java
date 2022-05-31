package com.team4.isamrs.dto.updation;

import com.team4.isamrs.validation.Bounds;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@Bounds
public class LoyaltyProgramCategoriesUpdationDTO {

    @Valid
    private List<LoyaltyProgramCategoryUpdationDTO> categories;

}
