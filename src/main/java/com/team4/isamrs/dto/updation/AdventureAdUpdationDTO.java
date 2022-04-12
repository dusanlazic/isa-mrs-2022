package com.team4.isamrs.dto.updation;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class AdventureAdUpdationDTO {
    @NotBlank
    @Size(max=40)
    private String title;

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

    @NotBlank
    @Size(max=150)
    private String instructorBio;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    @PositiveOrZero
    private BigDecimal cancellationFee;

    @NotNull
    @Size(min=3, max=10)
    private Set<Long> tagIds;

    @NotNull
    @Size(min=3, max=10)
    private Set<UUID> photoIds;

    @NotEmpty
    private Set<Long> fishingEquipmentIds;

    @NotNull
    @Valid
    private AddressUpdationDTO address;
}