package com.team4.isamrs.dto.creation;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

    private LocalDate availableAfter;

    private LocalDate availableUntil;

    @Size(max=500)
    private String rules;

    @NotBlank
    @Size(max=5)
    private String currency;

    @NotNull
    @Size(min=3, max=10)
    private Set<String> tagNames;

    @Size(min=3, max=10)
    private Set<UUID> photoIds;

    @Valid
    @Size(max=10)
    private List<OptionCreationDTO> options;

    @NotNull
    private LocalTime checkOutTime;

    @NotNull
    private LocalTime checkInTime;

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
    private Set<String> fishingEquipmentNames;

    @NotEmpty
    private Set<String> navigationalEquipmentNames;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    @PositiveOrZero
    private BigDecimal cancellationFee;

    @NotNull
    private BigDecimal pricePerDay;
}
