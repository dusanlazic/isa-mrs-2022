package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.ReservationSimpleDisplayDTO;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.User;
import com.team4.isamrs.repository.AdvertiserRepository;
import com.team4.isamrs.repository.ReservationReportRepository;
import com.team4.isamrs.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationReportRepository reservationReportRepository;

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ReservationSimpleDisplayDTO findById(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        if (!reservation.getAdvertisement().getAdvertiser().getUsername().equals(advertiser.getUsername()))
            throw new NoSuchElementException();

        return modelMapper.map(reservation, ReservationSimpleDisplayDTO.class);
    }

    public Page<ReservationSimpleDisplayDTO> findAll(Pageable pageable, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Advertiser advertiser = advertiserRepository.findById(user.getId()).orElseThrow();

        List<ReservationSimpleDisplayDTO> reservations = advertiser.getAds().stream()
                .flatMap(ad -> ad.getReservations().stream())
                .sorted(Comparator.comparing(Reservation::getStartDateTime).reversed())
                .map(r -> modelMapper.map(r, ReservationSimpleDisplayDTO.class)).toList();

        int fromIndex = Math.min((int)pageable.getOffset(), reservations.size());
        int toIndex = Math.min((fromIndex + pageable.getPageSize()), reservations.size());

        return new PageImpl<>(reservations.subList(fromIndex, toIndex), pageable, reservations.size());
    }

    public Page<ReservationSimpleDisplayDTO> findActive(Pageable pageable, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Advertiser advertiser = advertiserRepository.findById(user.getId()).orElseThrow();

        List<ReservationSimpleDisplayDTO> reservations = advertiser.getAds().stream()
                .flatMap(ad -> ad.getReservations().stream())
                .filter(r -> !r.getCancelled()
                        && r.getStartDateTime().isBefore(LocalDateTime.now())
                        && r.getEndDateTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Reservation::getStartDateTime).reversed())
                .map(r -> modelMapper.map(r, ReservationSimpleDisplayDTO.class))
                .collect(Collectors.toList());

        int fromIndex = Math.min((int)pageable.getOffset(), reservations.size());
        int toIndex = Math.min((fromIndex + pageable.getPageSize()), reservations.size());

        return new PageImpl<>(reservations.subList(fromIndex, toIndex), pageable, reservations.size());
    }

    public Page<ReservationSimpleDisplayDTO> findCompleted(Pageable pageable, Boolean hasReport, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Advertiser advertiser = advertiserRepository.findById(user.getId()).orElseThrow();

        List<ReservationSimpleDisplayDTO> reservations;
        if (hasReport != null && hasReport) {
            reservations = advertiser.getAds().stream()
                    .flatMap(ad -> ad.getReservations().stream())
                    .filter(r -> !r.getCancelled()
                            && r.getEndDateTime().isBefore(LocalDateTime.now())
                            && reservationReportRepository.existsReservationReportByReservation(r))
                    .sorted(Comparator.comparing(Reservation::getStartDateTime).reversed())
                    .map(r -> modelMapper.map(r, ReservationSimpleDisplayDTO.class))
                    .collect(Collectors.toList());
        } else if (hasReport != null) {
            reservations = advertiser.getAds().stream()
                    .flatMap(ad -> ad.getReservations().stream())
                    .filter(r -> !r.getCancelled()
                            && r.getEndDateTime().isBefore(LocalDateTime.now())
                            && !reservationReportRepository.existsReservationReportByReservation(r))
                    .sorted(Comparator.comparing(Reservation::getStartDateTime).reversed())
                    .map(r -> modelMapper.map(r, ReservationSimpleDisplayDTO.class))
                    .collect(Collectors.toList());
        } else {
            reservations = advertiser.getAds().stream()
                    .flatMap(ad -> ad.getReservations().stream())
                    .filter(r -> !r.getCancelled()
                            && r.getEndDateTime().isBefore(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Reservation::getStartDateTime).reversed())
                    .map(r -> modelMapper.map(r, ReservationSimpleDisplayDTO.class))
                    .collect(Collectors.toList());
        }

        int fromIndex = Math.min((int)pageable.getOffset(), reservations.size());
        int toIndex = Math.min((fromIndex + pageable.getPageSize()), reservations.size());

        return new PageImpl<>(reservations.subList(fromIndex, toIndex), pageable, reservations.size());
    }
}
