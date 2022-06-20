package com.team4.isamrs.dto.creation;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class ReservationRenewalCreationDTO {
    private Long reservationId;
    private Long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer attendees;
    private Set<SelectedOptionCreationDTO> selectedOptions = new HashSet<>();
}
