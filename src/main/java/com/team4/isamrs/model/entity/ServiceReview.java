package com.team4.isamrs.model.entity;

import com.team4.isamrs.model.enumeration.ApprovalStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ServiceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private Double rating;

    @Column
    private String comment;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private ApprovalStatus approvalStatus;
}
