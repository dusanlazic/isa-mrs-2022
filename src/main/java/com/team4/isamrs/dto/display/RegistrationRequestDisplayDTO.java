package com.team4.isamrs.dto.display;

import com.team4.isamrs.model.enumeration.AccountType;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationRequestDisplayDTO implements DisplayDTO {
    private Long id;
    private LocalDateTime createdAt;
    private AccountType accountType;
    private String explanation;
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private ApprovalStatus approvalStatus;
    private String rejectionReason;
}
