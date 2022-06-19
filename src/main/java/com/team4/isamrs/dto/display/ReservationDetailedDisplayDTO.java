package com.team4.isamrs.dto.display;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team4.isamrs.dto.creation.SelectedOptionCreationDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class ReservationDetailedDisplayDTO {
    private Long id;
    private String type;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime endDateTime;
    private AdvertisementDisplayDTO advertisement;
    private CustomerSimpleDisplayDTO customer;
    private BigDecimal calculatedPrice;
    private Integer attendees;
    private Set<SelectedOptionCreationDTO> selectedOptions = new HashSet<>();
    private boolean cancelled;
    private boolean canBeReviewed;
    private boolean canBeComplainedAbout;
    private boolean canBeReportedOn;
    private boolean canBeExtended;
}
