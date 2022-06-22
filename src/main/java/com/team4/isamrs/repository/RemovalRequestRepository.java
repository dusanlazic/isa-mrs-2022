package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.user.RemovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.Optional;

public interface RemovalRequestRepository extends JpaRepository<RemovalRequest, Long> {

    Optional<RemovalRequest> findByUserId(Long userId);

    Collection<RemovalRequest> findByApprovalStatusOrderByCreatedAt(ApprovalStatus approvalStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RemovalRequest r WHERE r.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<RemovalRequest> lockGetById(Long id);
}
