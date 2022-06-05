package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationReportDisplayDTO implements DisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private ReservationSimpleDisplayDTO reservation;
    private String comment;
    private Boolean penaltyRequested;
    private Boolean customerWasLate;
    private ApprovalStatus approvalStatus;
}