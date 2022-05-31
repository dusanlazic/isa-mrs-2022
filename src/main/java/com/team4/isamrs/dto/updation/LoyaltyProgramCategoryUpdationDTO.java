package com.team4.isamrs.dto.updation;

import com.team4.isamrs.validation.MultiplicationFactor;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@MultiplicationFactor
public class LoyaltyProgramCategoryUpdationDTO {
    @NotBlank
    @Size(min = 2, max = 30)
    private String title;

    @NotNull
    @Pattern(regexp = "CUSTOMER|ADVERTISER", message = "Value must be 'CUSTOMER' or 'ADVERTISER'")
    private String targetedAccountType;

    @NotNull
    @PositiveOrZero
    private Integer pointsLowerBound;

    @NotNull
    @PositiveOrZero
    private Integer pointsUpperBound;

    @NotNull
    private BigDecimal multiply;

    private Boolean delete;
}
