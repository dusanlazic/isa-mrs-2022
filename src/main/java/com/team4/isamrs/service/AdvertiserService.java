package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.AdvertisementDisplayDTO;
import com.team4.isamrs.dto.display.AdvertisementSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ReservationSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.model.review.ServiceProviderReview;
import com.team4.isamrs.model.user.*;
import com.team4.isamrs.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdvertiserService {

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Set<AdvertisementSimpleDisplayDTO> getAdvertisements(Long id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow();
        return advertiser.getAds().stream()
                .map(e -> modelMapper.map(e, AdvertisementSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public Set<ServiceReviewDisplayDTO> getServiceProviderReview(Long id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow();
        return advertiser.getAds().stream()
                .map(e -> modelMapper.map(e, ServiceReviewDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public double findRating(Long id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow();
        Set<ServiceProviderReview> advertisements = advertiser.getReviews();
        double rating = 0;
        if (advertisements.size() > 0)
            rating = advertisements.stream().mapToDouble(ServiceProviderReview::getRating).sum() / advertisements.size();
        return Math.round(rating * 100.0) / 100.0;
    }

    public Page<ReservationSimpleDisplayDTO> findActiveReservations(Pageable pageable, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Advertiser advertiser = advertiserRepository.findById(user.getId()).orElseThrow();

        List<ReservationSimpleDisplayDTO> reservations = advertiser.getAds().stream()
                .flatMap(ad -> ad.getReservations().stream())
                .filter(r -> !r.getCancelled()
                        && r.getStartDateTime().isBefore(LocalDateTime.now())
                        && r.getEndDateTime().isAfter(LocalDateTime.now()))
                .map(r -> modelMapper.map(r, ReservationSimpleDisplayDTO.class))
                .collect(Collectors.toList());

        int fromIndex = Math.min((int)pageable.getOffset(), reservations.size());
        int toIndex = Math.min((fromIndex + pageable.getPageSize()), reservations.size());

        return new PageImpl<ReservationSimpleDisplayDTO>
                (reservations.subList(fromIndex, toIndex), pageable, reservations.size());
    }
}