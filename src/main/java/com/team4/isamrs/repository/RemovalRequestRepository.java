package com.team4.isamrs.repository;

import com.team4.isamrs.model.user.RemovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemovalRequestRepository extends JpaRepository<RemovalRequest, Long> {
}
