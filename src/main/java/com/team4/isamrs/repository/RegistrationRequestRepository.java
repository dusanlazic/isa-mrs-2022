package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.user.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.Optional;

public interface RegistrationRequestRepository  extends JpaRepository<RegistrationRequest, Long> {

    Collection<RegistrationRequest> findByApprovalStatusOrderByCreatedAt(ApprovalStatus approvalStatus);

    Optional<RegistrationRequest> findByUsername(String username);

    Optional<RegistrationRequest> findByPhoneNumber(String phoneNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RegistrationRequest r WHERE r.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<RegistrationRequest> lockGetById(Long id);
}
