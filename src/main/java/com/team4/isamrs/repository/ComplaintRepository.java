package com.team4.isamrs.repository;

import com.team4.isamrs.model.complaint.Complaint;
import com.team4.isamrs.model.enumeration.ResponseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Collection<Complaint> findByResponseStatusEquals(ResponseStatus status);

    Optional<Complaint> findByIdAndResponseStatusEquals(Long id, ResponseStatus status);
}
