package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.user.RemovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface RemovalRequestRepository extends JpaRepository<RemovalRequest, Long> {

    Optional<RemovalRequest> findByUserId(Long userId);

    Collection<RemovalRequest> findByApprovalStatusOrderByCreatedAt(ApprovalStatus approvalStatus);
}
