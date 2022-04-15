package com.team4.isamrs.repository;

import com.team4.isamrs.model.review.ServiceReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceReviewRepository  extends JpaRepository<ServiceReview, Long> {
}
