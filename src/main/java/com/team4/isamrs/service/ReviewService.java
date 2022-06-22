package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ReviewCreationDTO;
import com.team4.isamrs.dto.display.ReviewAdminDisplayDTO;
import com.team4.isamrs.dto.updation.ReviewResponseDTO;
import com.team4.isamrs.exception.ActionNotAllowedException;
import com.team4.isamrs.exception.ReviewAlreadyResolvedException;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.AdvertisementRepository;
import com.team4.isamrs.repository.ReservationRepository;
import com.team4.isamrs.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    public Review create(Long id, ReviewCreationDTO dto, Authentication auth) {
        Customer customer = (Customer) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();

        if (!hasCompletedReservation(customer, advertisement))
            throw new ActionNotAllowedException("You cannot create a review as you do not have a completed reservation for this advertisement.");

        Review review = new Review();
        review.setCreatedAt(LocalDateTime.now());
        review.setAdvertisement(advertisement);
        review.setCustomer(customer);
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setApprovalStatus(ApprovalStatus.PENDING);

        return reviewRepository.save(review);
    }

    public Collection<ReviewAdminDisplayDTO> findAllPending() {
        return reviewRepository.findByApprovalStatusEquals(ApprovalStatus.PENDING)
                .stream().map(complaint -> modelMapper.map(complaint, ReviewAdminDisplayDTO.class))
                .collect(Collectors.toList());
    }

    public void respondToReview(Long id, ReviewResponseDTO dto) {
        Review review = reviewRepository.findById(id).orElseThrow();
        if (!review.getApprovalStatus().equals(ApprovalStatus.PENDING))
            throw new ReviewAlreadyResolvedException();

        if (dto.getApprove()) {
            review.setApprovalStatus(ApprovalStatus.APPROVED);
            emailService.sendNewReviewEmail(review, 4.5);
        } else {
            review.setApprovalStatus(ApprovalStatus.REJECTED);
        }

        reviewRepository.save(review);
    }

    private Boolean hasCompletedReservation(Customer customer, Advertisement advertisement) {
        return reservationRepository.existsByAdvertisementEqualsAndCustomerEqualsAndCancelledIsFalseAndEndDateTimeBefore(
                advertisement, customer, LocalDateTime.now());
    }
}
