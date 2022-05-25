package com.team4.isamrs.model.review;

import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ServiceProviderReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertiser_id")
    private Advertiser advertiser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "approval_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ApprovalStatus approvalStatus;
}