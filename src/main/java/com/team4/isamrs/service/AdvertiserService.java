package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.AccountDisplayDTO;
import com.team4.isamrs.dto.display.AdvertisementSimpleDisplayDTO;
import com.team4.isamrs.dto.display.AverageRatingDisplayDTO;
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

    public AverageRatingDisplayDTO getAverageRating(Long id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow();
        Double value = advertiserRepository.findAverageRatingForAdvertiser(advertiser).orElse(0.0);

        return new AverageRatingDisplayDTO(Math.round(value * 100.0) / 100.0);
    }
}