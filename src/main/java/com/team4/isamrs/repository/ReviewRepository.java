package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Collection<Review> findByApprovalStatusEquals(ApprovalStatus status);

    Collection<Review> findByCustomer(Customer customer);
}
