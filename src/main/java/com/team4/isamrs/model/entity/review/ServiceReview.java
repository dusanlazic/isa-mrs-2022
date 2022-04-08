package com.team4.isamrs.model.entity.review;

import com.team4.isamrs.model.entity.user.Customer;
import com.team4.isamrs.model.entity.advertisement.Advertisement;
import com.team4.isamrs.model.enumeration.ApprovalStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ServiceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "approvalStatus", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ApprovalStatus approvalStatus;
}
