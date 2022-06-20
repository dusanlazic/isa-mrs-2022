package com.team4.isamrs.dto.display;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class QuickReservationSimpleDisplayDTO {
    private Long id;
    private LocalDateTime validAfter;
    private LocalDateTime validUntil;
    private BigDecimal newPrice;
    private BigDecimal calculatedOldPrice;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer capacity;
    private Set<SelectedOptionDisplayDTO> selectedOptions = new HashSet<>();
}
