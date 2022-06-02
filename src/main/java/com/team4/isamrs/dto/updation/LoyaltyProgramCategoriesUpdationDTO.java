package com.team4.isamrs.dto.updation;

import com.team4.isamrs.validation.Bounds;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Data
@Bounds
public class LoyaltyProgramCategoriesUpdationDTO {

    @Valid
    private List<LoyaltyProgramCategoryUpdationDTO> categories;

    private Set<Long> delete;

}
