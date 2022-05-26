package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ResortAdCreationDTO;
import com.team4.isamrs.dto.display.ResortAdDisplayDTO;
import com.team4.isamrs.dto.display.ResortAdSimpleDisplayDTO;
import org.springframework.data.domain.Page;
import com.team4.isamrs.dto.updation.AvailabilityPeriodUpdationDTO;
import com.team4.isamrs.exception.IdenticalAvailabilityDatesException;
import com.team4.isamrs.exception.ReservationsInUnavailabilityPeriodException;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.resort.ResortReservation;
import com.team4.isamrs.repository.ResortReservationRepository;
import org.springframework.data.domain.PageRequest;
import com.team4.isamrs.dto.updation.ResortAdUpdationDTO;
import com.team4.isamrs.model.resort.ResortAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.ResortAdRepository;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResortAdService {

    @Autowired
    private ResortAdRepository resortAdRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ResortReservationRepository reservationRepository;

    public Collection<ResortAdDisplayDTO> findAll() {
        return resortAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, ResortAdDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public ResortAdDisplayDTO findById(Long id) {
        ResortAd resortAd = resortAdRepository.findById(id).orElseThrow();
        return modelMapper.map(resortAd, ResortAdDisplayDTO.class);
    }

    public Collection<ResortAdSimpleDisplayDTO> findTopSix() {
        return resortAdRepository.findAll(PageRequest.of(0, 6)).stream()
                .map(e -> modelMapper.map(e, ResortAdSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public Page<ResortAdSimpleDisplayDTO> search(int page) {
        return resortAdRepository.findAll(PageRequest.of(page, 20))
                .map(e -> modelMapper.map(e, ResortAdSimpleDisplayDTO.class));
    }

    public ResortAd create(ResortAdCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        ResortAd resortAd = modelMapper.map(dto, ResortAd.class);
        resortAd.setAdvertiser(advertiser);
        resortAd.verifyPhotosOwnership(advertiser);

        resortAdRepository.save(resortAd);
        tagRepository.saveAll(resortAd.getTags());
        return resortAd;
    }

    public void delete(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        ResortAd resortAd = resortAdRepository.findResortAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        resortAd.getTags().forEach(e -> e.getAdvertisements().remove(resortAd));

        resortAdRepository.delete(resortAd);
    }

    public void update(Long id, ResortAdUpdationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        ResortAd resortAd = resortAdRepository.findAdventureAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        modelMapper.map(dto, resortAd);

        resortAd.verifyPhotosOwnership(advertiser);

        tagRepository.saveAll(resortAd.getTags());
        resortAdRepository.save(resortAd);
    }

    public void updateAvailabilityPeriod(Long id, AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        if (dto.getAvailableUntil() != null && dto.getAvailableAfter() != null &&
                dto.getAvailableUntil().equals(dto.getAvailableAfter()))
            throw new IdenticalAvailabilityDatesException();

        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        ResortAd resortAd = resortAdRepository.findResortAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        if (!(dto.getAvailableUntil() == null && dto.getAvailableAfter() == null)) {
            // only available until is defined
            if (dto.getAvailableAfter() == null) {
                Set<ResortReservation> reservations = reservationRepository.findReservationsByEndDateAfter(dto.getAvailableUntil());
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // only available after is defined
            else if (dto.getAvailableUntil() == null) {
                Set<ResortReservation> reservations = reservationRepository.findReservationsByStartDateBefore(dto.getAvailableAfter());
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // both are defined
            // availability period
            else if (dto.getAvailableAfter().isBefore(dto.getAvailableUntil())) {
                Set<ResortReservation> reservations = reservationRepository.findReservationsByStartDateBeforeOrEndDateAfter(dto.getAvailableAfter(), dto.getAvailableUntil());
                reservations.removeIf(Reservation::getCancelled);
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // unavailability period
            else {
                Set<ResortReservation> reservations = reservationRepository.findReservationsByStartDateBeforeAndEndDateAfter(dto.getAvailableAfter(), dto.getAvailableUntil());
                reservations.removeIf(Reservation::getCancelled);
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
        }

        resortAd.setAvailableAfter(dto.getAvailableAfter());
        resortAd.setAvailableUntil(dto.getAvailableUntil());
        resortAdRepository.save(resortAd);
    }
}
