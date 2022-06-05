package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.AccountDisplayDTO;
import com.team4.isamrs.dto.display.AdvertisementSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.model.review.ServiceProviderReview;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.AdvertiserRepository;
import com.team4.isamrs.repository.ReservationReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdvertiserService {

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Autowired
    private ReservationReportRepository reservationReportRepository;

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

    public Set<ServiceReviewDisplayDTO> getServiceProviderReview(Long id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow();
        return advertiser.getAds().stream()
                .flatMap(ad -> ad.getReviews().stream())
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
}