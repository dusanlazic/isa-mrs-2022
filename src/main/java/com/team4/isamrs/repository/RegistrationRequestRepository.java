package com.team4.isamrs.repository;

import com.team4.isamrs.model.user.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRequestRepository  extends JpaRepository<RegistrationRequest, Long> {
}
