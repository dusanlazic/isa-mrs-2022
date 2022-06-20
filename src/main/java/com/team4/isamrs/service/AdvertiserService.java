package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.*;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.advertisement.AdventureAd;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.advertisement.ResortAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.*;
import org.apache.tika.utils.StringUtils;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdvertiserService {

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AccountDisplayDTO findById(Long id) {
        return modelMapper.map(advertiserRepository.findById(id).orElseThrow(), AccountDisplayDTO.class);
    }

    public Set<AdvertisementSimpleDisplayDTO> getAdvertisements(Long id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow();
        return advertiser.getAds().stream()
                .map(e -> modelMapper.map(e, AdvertisementSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public AverageRatingDisplayDTO getAverageRating(Long id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow();
        Double value = advertiserRepository.findAverageRatingForAdvertiser(advertiser).orElse(0.0);

        return new AverageRatingDisplayDTO(Math.round(value * 100.0) / 100.0);
    }

    public Set<ReviewPublicDisplayDTO> getApprovedReviews(Long id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow();
        return reviewRepository.findReviewsForAdsByAdvertiser(advertiser).stream()
                .filter(e -> e.getApprovalStatus().equals(ApprovalStatus.APPROVED))
                .map(e -> modelMapper.map(e, ReviewPublicDisplayDTO.class))
                .collect(Collectors.toSet());
    }
    
    public Page<ReservationSimpleDisplayDTO> getReservations(Authentication auth, Pageable pageable) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        return reservationRepository.findReservationsForAdvertiser(advertiser, pageable).map(reservation -> {
            ReservationSimpleDisplayDTO dto = new ReservationSimpleDisplayDTO();
            dto.setCustomer(modelMapper.map(reservation.getCustomer(), CustomerSimpleDisplayDTO.class));
            dto.setStartDateTime(reservation.getStartDateTime());
            dto.setEndDateTime(reservation.getEndDateTime());
            dto.setCancelled(reservation.getCancelled());
            dto.setCalculatedPrice(reservation.getCalculatedPrice());
            dto.setId(reservation.getId());
            dto.setCreatedAt(reservation.getCreatedAt());
            dto.setCanBeReportedOn(reportRepository.findReservationReportByReservation(reservation).isEmpty() && LocalDateTime.now().isAfter(reservation.getEndDateTime()));
            dto.setCanBeRenewed(LocalDateTime.now().isAfter(reservation.getStartDateTime()) && LocalDateTime.now().isBefore(reservation.getEndDateTime()));

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

    public Page<AdvertisementSimpleDisplayDTO> getPaginatedAdvertisements(Authentication auth, Pageable pageable) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        return advertisementRepository.findAdvertisementsByAdvertiser(advertiser, pageable).map( advertisement -> modelMapper.map(advertisement, AdvertisementSimpleDisplayDTO.class));
    }
}