package com.team4.isamrs.dto.creation;

import com.team4.isamrs.dto.display.DailyPriceDisplayDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class BoatAdCreationDTO {
    @NotBlank
    @Size(max=40)
    private String title;

    @NotNull
    @Valid
    private AddressCreationDTO address;

    @NotBlank
    @Size(max=250)
    private String description;

    @Size(max=100)
    private String pricingDescription;

    private LocalDateTime availableAfter;

    private LocalDateTime availableUntil;

    @Size(max=500)
    private String rules;

    @NotBlank
    @Size(max=5)
    private String currency;

    @NotNull
    @Size(min=3, max=10)
    private Set<Long> tagIds;

    @NotNull
    @Size(min=3, max=10)
    private Set<UUID> photoIds;

    @NotEmpty
    @Valid
    private Set<OptionCreationDTO> options;

    @NotNull
    private LocalDateTime CheckOutTime;

    @NotNull
    private LocalDateTime CheckInTime;

    @NotBlank
    @Size(max=20)
    private String boatType;

    @NotBlank
    @Size(max=20)
    private String boatLength;

    @NotBlank
    @Size(max=20)
    private String engineNumber;

    @NotBlank
    @Size(max=20)
    private String enginePower;

    @NotBlank
    @Size(max=20)
    private String boatSpeed;

    @NotEmpty
    private Set<Long> fishingEquipmentIds;

    @NotEmpty
    private Set<Long> navigationalEquipmentIds;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    @PositiveOrZero
    private BigDecimal cancellationFee;

    @NotEmpty
    @Valid
    private Set<DailyPriceCreationDTO> prices;

}
