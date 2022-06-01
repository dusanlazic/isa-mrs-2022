package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemovalRequestDisplayDTO implements DisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private SessionDisplayDTO user;
    private String explanation;
    private ApprovalStatus approvalStatus;
    private String rejectionReason;
}
