package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.enumeration.ResponseStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintDisplayDTO implements DisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private ReservationSimpleDisplayDTO reservation;
    private CustomerSimpleDisplayDTO customer;
    private String comment;
    private Boolean customerWasLate;
    private ResponseStatus responseStatus;
}