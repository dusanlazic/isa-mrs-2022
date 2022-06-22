package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Collection<Review> findByApprovalStatusEquals(ApprovalStatus status);
    Collection<Review> findByCustomer(Customer customer);

    @Query("SELECT r FROM Review r WHERE r.approvalStatus = 1 AND r.advertisement.advertiser = ?1")
    Collection<Review> findReviewsForAdsByAdvertiser(Advertiser advertiser);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Review r WHERE r.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<Review> lockGetById(Long id);
}
