package com.team4.isamrs.dto;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AdventureAdFormDto {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String pricingDescription;
    private LocalDateTime availableAfter;
    private LocalDateTime availableUntil;
    private String rules;
    private String currency;
    private String instructorBio;
    private Integer capacity;
    private BigDecimal cancellationFee;

    private Long advertiserId;
    private Set<Long> tagIds;
    private Set<Long> photoIds;
    private Set<Long> fishingEquipmentIds;

    private AddressFormDto address;
    private Set<OptionFormDto> options;
    private Set<HourlyPriceFormDto> prices;
}
