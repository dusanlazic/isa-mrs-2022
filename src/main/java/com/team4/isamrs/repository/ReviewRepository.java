package com.team4.isamrs.repository;

import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Collection<Review> findByApprovalStatusEquals(ApprovalStatus status);

    @Query("SELECT r FROM Review r WHERE r.approvalStatus = 1 AND r.advertisement.advertiser = ?1")
    Collection<Review> findReviewsForAdsByAdvertiser(Advertiser advertiser);
}
