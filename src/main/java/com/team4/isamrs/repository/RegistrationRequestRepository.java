package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.user.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface RegistrationRequestRepository  extends JpaRepository<RegistrationRequest, Long> {

    Collection<RegistrationRequest> findByApprovalStatusOrderByCreatedAt(ApprovalStatus approvalStatus);

    Optional<RegistrationRequest> findByUsername(String username);

    Optional<RegistrationRequest> findByPhoneNumber(String phoneNumber);
}
