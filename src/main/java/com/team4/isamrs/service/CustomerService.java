package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.*;
import com.team4.isamrs.model.advertisement.AdventureAd;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.advertisement.ResortAd;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.*;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return modelMapper.map(customer, returnType);
    }

    public Collection<ReviewPublicDisplayDTO> getReviews(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return reviewRepository.findByCustomer(customer).stream()
                .filter(review -> review.getApprovalStatus().equals(ApprovalStatus.APPROVED))
                .map(review -> {
                    ReviewPublicDisplayDTO dto = new ReviewPublicDisplayDTO();
                    dto.setCustomer(modelMapper.map(customer, CustomerPublicDisplayDTO.class));
                    dto.setComment(review.getComment());
                    dto.setRating(review.getRating());
                    dto.setId(review.getId());
                    dto.setCreatedAt(review.getCreatedAt());

                    // domain mapper sucks
                    Advertisement advertisement = (Advertisement) Hibernate.unproxy(review.getAdvertisement());
                    if (advertisement instanceof ResortAd) {
                        ResortAd concreteAd = (ResortAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof BoatAd) {
                        BoatAd concreteAd = (BoatAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof AdventureAd) {
                        AdventureAd concreteAd = (AdventureAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }

                    dto.setAdvertiser(modelMapper.map(advertisement.getAdvertiser(), SessionDisplayDTO.class));
                    return dto;
                }).collect(Collectors.toSet());
    }

    public Page<ReservationSimpleDisplayDTO> getPreviousReservations(Pageable pageable, Authentication auth) {
        Customer customer = (Customer) auth.getPrincipal();
        return reservationRepository.findReservationsByCustomerEqualsAndStartDateTimeBefore(customer, LocalDateTime.now(), pageable)
                .map(reservation -> {
                    ReservationSimpleDisplayDTO dto = new ReservationSimpleDisplayDTO();
                    dto.setCustomer(modelMapper.map(customer, CustomerSimpleDisplayDTO.class));
                    dto.setStartDateTime(reservation.getStartDateTime());
                    dto.setEndDateTime(reservation.getEndDateTime());
                    dto.setCancelled(reservation.getCancelled());
                    dto.setCalculatedPrice(reservation.getCalculatedPrice());
                    dto.setId(reservation.getId());
                    dto.setCreatedAt(reservation.getCreatedAt());

                    // domain mapper sucks
                    Advertisement advertisement = (Advertisement) Hibernate.unproxy(reservation.getAdvertisement());
                    if (advertisement instanceof ResortAd) {
                        ResortAd concreteAd = (ResortAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof BoatAd) {
                        BoatAd concreteAd = (BoatAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof AdventureAd) {
                        AdventureAd concreteAd = (AdventureAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }

                    dto.setCanBeReviewed(!reservation.getCancelled()
                            && !hasUserReviewedAdvertisement(customer, advertisement));
                    dto.setCanBeComplainedAbout(!reservation.getCancelled()
                            && !hasUserComplainedAboutAdvertisement(customer, advertisement));
                    return dto;
                });
    }

    private boolean hasUserComplainedAboutAdvertisement(Customer customer, Advertisement advertisement) {
        return complaintRepository.findByCustomerAndAdvertisement(customer, advertisement).size() > 0;
    }

    private boolean hasUserReviewedAdvertisement(Customer customer, Advertisement advertisement) {
        return advertisement.getReviews().stream().anyMatch(review -> review.getCustomer().getId().equals(customer.getId()));
    }

    public Page<ReservationSimpleDisplayDTO> getUpcomingReservations(PageRequest pageable, Authentication auth) {
        Customer customer = (Customer) auth.getPrincipal();
        return reservationRepository.findReservationsByCustomerEqualsAndStartDateTimeAfter(customer, LocalDateTime.now(), pageable)
                .map(reservation -> {
                    ReservationSimpleDisplayDTO dto = new ReservationSimpleDisplayDTO();
                    dto.setCustomer(modelMapper.map(customer, CustomerSimpleDisplayDTO.class));
                    dto.setStartDateTime(reservation.getStartDateTime());
                    dto.setEndDateTime(reservation.getEndDateTime());
                    dto.setCancelled(reservation.getCancelled());
                    dto.setCalculatedPrice(reservation.getCalculatedPrice());
                    dto.setId(reservation.getId());
                    dto.setCreatedAt(reservation.getCreatedAt());
                    dto.setCanBeReviewed(false);

                    // domain mapper sucks
                    Advertisement advertisement = (Advertisement) Hibernate.unproxy(reservation.getAdvertisement());
                    if (advertisement instanceof ResortAd) {
                        ResortAd concreteAd = (ResortAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof BoatAd) {
                        BoatAd concreteAd = (BoatAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }
                    else if (advertisement instanceof AdventureAd) {
                        AdventureAd concreteAd = (AdventureAd) advertisement;
                        dto.setAdvertisement(modelMapper.map(concreteAd, AdvertisementSimpleDisplayDTO.class));
                    }

                    return dto;
                });
    }
}
