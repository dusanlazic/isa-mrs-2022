package com.team4.isamrs.dto.updation;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ResortAdUpdationDTO {
    @NotBlank
    @Size(max=40)
    private String title;

    @NotNull
    @Valid
    private AddressUpdationDTO address;

    @NotBlank
    @Size(max=250)
    private String description;

    @NotNull
    private BigDecimal pricePerDay;

    @Size(max=100)
    private String pricingDescription;

    @Size(max=500)
    private String rules;

    @NotBlank
    @Size(max=5)
    private String currency;

    @NotNull
    @Size(min=3, max=10)
    private Set<String> tagNames;

    @NotNull
    @Size(max=10)
    private Set<UUID> photoIds;

    @Size(max=10)
    @Valid
    private List<OptionUpdationDTO> options;

    @NotEmpty
    private List<Integer> bedCountPerRoom;

    @NotNull
    private LocalTime checkOutTime;

    @NotNull
    private LocalTime checkInTime;
}
