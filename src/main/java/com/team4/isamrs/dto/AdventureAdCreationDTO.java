package com.team4.isamrs.dto;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class AdventureAdCreationDTO {
    /*
    TODO: Perform whitespace checks using custom annotations or regex.

    It's important to understand that there are three types of fields when creating a new entity.
    1. Data fields:
       Strings, integers, decimals and other similar types that are serializable by default.
       e.g. title, description, capacity

    2. "New Entity" fields:
       Those contain their own set of fields. These fields are used for creating entities related
       to the main entity we are creating.
       e.g. address, options, prices

    3. "Existing Entity" fields:
       Represents an ID of an already existing entity.
       e.g. advertiserId, tagIds
     */

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

    private Set<Long> fishingEquipmentIds;

    @NotNull
    @Valid
    private AddressCreationDTO address;

    @NotEmpty
    @Valid
    private Set<OptionCreationDTO> options;

    @NotEmpty
    @Valid
    private Set<HourlyPriceCreationDTO> prices;
}
