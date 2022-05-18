package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.display.BoatAdDisplayDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.AvailabilityPeriodUpdationDTO;
import com.team4.isamrs.dto.updation.BoatAdUpdationDTO;
import com.team4.isamrs.exception.IdenticalAvailabilityDatesException;
import com.team4.isamrs.exception.ReservationsInUnavailabilityPeriodException;
import com.team4.isamrs.model.boat.BoatAd;
import com.team4.isamrs.model.boat.BoatReservation;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BoatAdService {

    @Autowired
    private BoatAdRepository boatAdRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    @Autowired
    private NavigationalEquipmentRepository navigationalEquipmentRepository;

    @Autowired
    private BoatReservationRepository reservationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAll(Class<T> returnType) {
        return boatAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public Collection<BoatAdDisplayDTO> findTopTen() {
        return boatAdRepository.findAll(PageRequest.of(0, 10)).stream()
                .map(e -> modelMapper.map(e, BoatAdDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        BoatAd boatAd = boatAdRepository.findById(id).orElseThrow();
        return modelMapper.map(boatAd, returnType);
    }

    public BoatAd create(BoatAdCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        BoatAd boatAd = modelMapper.map(dto, BoatAd.class);
        boatAd.setAdvertiser(advertiser);
        boatAd.verifyPhotosOwnership(advertiser);

        boatAdRepository.save(boatAd);
        tagRepository.saveAll(boatAd.getTags());
        fishingEquipmentRepository.saveAll(boatAd.getFishingEquipment());
        navigationalEquipmentRepository.saveAll(boatAd.getNavigationalEquipment());
        return boatAd;
    }

    public void delete(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        BoatAd boatAd = boatAdRepository.findBoatAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        boatAd.getNavigationalEquipment().forEach(e -> e.getAdvertisements().remove(boatAd));
        boatAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(boatAd));
        boatAd.getTags().forEach(e -> e.getAdvertisements().remove(boatAd));

        boatAdRepository.delete(boatAd);
    }

    public void update(Long id, BoatAdUpdationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        BoatAd boatAd = boatAdRepository.findBoatAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        modelMapper.map(dto, boatAd);

        boatAd.verifyPhotosOwnership(advertiser);

        fishingEquipmentRepository.saveAll(boatAd.getFishingEquipment());
        tagRepository.saveAll(boatAd.getTags());
        boatAdRepository.save(boatAd);
    }

    public void updateAvailabilityPeriod(Long id, AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        if (dto.getAvailableUntil() != null && dto.getAvailableAfter() != null &&
                dto.getAvailableUntil().equals(dto.getAvailableAfter()))
            throw new IdenticalAvailabilityDatesException();

        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        BoatAd boatAd = boatAdRepository.findBoatAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        if (!(dto.getAvailableUntil() == null && dto.getAvailableAfter() == null)) {
            // only available until is defined
            if (dto.getAvailableAfter() == null) {
                Set<BoatReservation> reservations = reservationRepository.findReservationsByEndDateAfter(dto.getAvailableUntil());
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // only available after is defined
            else if (dto.getAvailableUntil() == null) {
                Set<BoatReservation> reservations = reservationRepository.findReservationsByStartDateBefore(dto.getAvailableAfter());
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // both are defined
            // availability period
            else if (dto.getAvailableAfter().isBefore(dto.getAvailableUntil())) {
                Set<BoatReservation> reservations = reservationRepository.findReservationsByStartDateBeforeOrEndDateAfter(dto.getAvailableAfter(), dto.getAvailableUntil());
                reservations.removeIf(Reservation::getCancelled);
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // unavailability period
            else {
                Set<BoatReservation> reservations = reservationRepository.findReservationsByStartDateBeforeAndEndDateAfter(dto.getAvailableAfter(), dto.getAvailableUntil());
                reservations.removeIf(Reservation::getCancelled);
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
        }

        boatAd.setAvailableAfter(dto.getAvailableAfter());
        boatAd.setAvailableUntil(dto.getAvailableUntil());
        boatAdRepository.save(boatAd);
    }
}
