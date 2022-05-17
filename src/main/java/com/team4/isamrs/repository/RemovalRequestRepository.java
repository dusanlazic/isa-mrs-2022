package com.team4.isamrs.repository;

import com.team4.isamrs.model.user.RegistrationRequest;
import com.team4.isamrs.model.user.RemovalRequest;
import com.team4.isamrs.model.user.User;
import net.bytebuddy.implementation.bytecode.Removal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RemovalRequestRepository extends JpaRepository<RemovalRequest, Long> {
    Optional<RemovalRequest> findByUserId(Long userId);
}
