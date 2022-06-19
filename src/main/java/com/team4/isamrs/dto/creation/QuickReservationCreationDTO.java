package com.team4.isamrs.dto.creation;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class QuickReservationCreationDTO {

    @NotNull
    private Long advertisementId;

    @NotNull
    private LocalDate validAfter;

    @NotNull
    private LocalDate validUntil;

    @NotNull
    private BigDecimal newPrice;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer capacity;

    @NotNull
    @Valid
    private Set<SelectedOptionCreationDTO> selectedOptions = new HashSet<>();
}
