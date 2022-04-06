package com.team4.isamrs.model.entity;

import com.team4.isamrs.model.enumeration.ApprovalStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RemovalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String explanation;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private ApprovalStatus approvalStatus;

    @Column
    private String response;
}
