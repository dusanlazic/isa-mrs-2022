package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.complaint.Complaint;
import com.team4.isamrs.model.enumeration.ResponseStatus;
import com.team4.isamrs.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Collection<Complaint> findByResponseStatusEquals(ResponseStatus status);

    Optional<Complaint> findByIdAndResponseStatusEquals(Long id, ResponseStatus status);

    Collection<Complaint> findByCustomerAndAdvertisement(Customer customer, Advertisement advertisement);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Complaint c WHERE c.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<Complaint> lockGetById(Long id);
}
