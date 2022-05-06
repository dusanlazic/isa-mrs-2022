package com.team4.isamrs.repository;

import com.team4.isamrs.model.user.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRequestRepository  extends JpaRepository<RegistrationRequest, Long> {

    Optional<RegistrationRequest> findByUsername(String username);

    Optional<RegistrationRequest> findByPhoneNumber(String phoneNumber);
}
