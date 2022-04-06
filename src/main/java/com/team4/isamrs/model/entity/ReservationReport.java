package com.team4.isamrs.model.entity;

import com.team4.isamrs.model.enumeration.ApprovalStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ReservationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @OneToOne
    private Reservation reservation;

    @Column
    private String comment;

    @Column
    private Boolean penaltyRequested;

    @Column
    private Boolean customerWasLate;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private ApprovalStatus approvalStatus;
}
