package com.team4.isamrs.model.entity;

import com.team4.isamrs.model.enumeration.AccountType;
import com.team4.isamrs.model.enumeration.ApprovalStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RegistrationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private AccountType accountType;

    @Column
    private String explanation;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String emailAddress;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String phoneNumber;

    @Column
    private String passwordHash;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private ApprovalStatus approvalStatus;

    @Column
    private String rejectionReason;
}
