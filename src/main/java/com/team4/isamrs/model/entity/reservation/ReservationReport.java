package com.team4.isamrs.model.entity.reservation;

import com.team4.isamrs.model.entity.reservation.Reservation;
import com.team4.isamrs.model.enumeration.ApprovalStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ReservationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne
    private Reservation reservation;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "penaltyRequested", nullable = false)
    private Boolean penaltyRequested;

    @Column(name = "customer_was_late", nullable = false)
    private Boolean customerWasLate;

    @Column(name = "approval_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ApprovalStatus approvalStatus;
}
