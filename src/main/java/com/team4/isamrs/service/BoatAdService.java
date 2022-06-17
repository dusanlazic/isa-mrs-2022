package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.display.BoatAdSimpleDisplayDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.BoatAdUpdationDTO;
import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoatAdService {

    @Autowired
    private BoatAdRepository boatAdRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    @Autowired
    private NavigationalEquipmentRepository navigationalEquipmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAll(Class<T> returnType) {
        return boatAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }


    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        BoatAd boatAd = boatAdRepository.findById(id).orElseThrow();
        return modelMapper.map(boatAd, returnType);
    }

    public Collection<BoatAdSimpleDisplayDTO> findTopSix() {
        return boatAdRepository.findAll(PageRequest.of(0, 6)).stream()
                .map(e -> modelMapper.map(e, BoatAdSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public Page<BoatAdSimpleDisplayDTO> search(String where, int guests, LocalDateTime startDate,
                                               LocalDateTime endDate, Pageable pageable,
                                               String sorting, boolean descending) {
        List<BoatAdSimpleDisplayDTO> finalBoats;
        List<BoatAd> candidateBoatAds = boatAdRepository.search(where, guests);
        finalBoats = candidateBoatAds.stream()
                .filter(b -> isAvailable(b, startDate, endDate))
                .map(b -> modelMapper.map(b, BoatAdSimpleDisplayDTO.class))
                .collect(Collectors.toList());

        if (sorting.equals("price"))
            finalBoats.sort(Comparator.comparing(BoatAdSimpleDisplayDTO::getPricePerDay));
        // else sort by average rating
        if (descending) Collections.reverse(finalBoats);

        int fromIndex = Math.min((int)pageable.getOffset(), finalBoats.size());
        int toIndex = Math.min((fromIndex + pageable.getPageSize()), finalBoats.size());

        return new PageImpl<BoatAdSimpleDisplayDTO>
                (finalBoats.subList(fromIndex, toIndex), pageable, finalBoats.size());

    }

    private boolean isAvailable(BoatAd boatAd, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null && endDate == null) return true;
        if (startDate == null) startDate = LocalDateTime.now();
        if (endDate == null) endDate = LocalDateTime.parse("2200-01-01T00:00");
        LocalDateTime adjustedStartDate, adjustedEndDate;
        LocalDate availableUntil = boatAd.getAvailableUntil();
        LocalDate availableAfter = boatAd.getAvailableAfter();

        if (availableUntil == null && availableAfter == null) {
            adjustedStartDate = startDate;
            adjustedEndDate = endDate;
        }
        else if (availableUntil != null && availableAfter == null) {
            if (availableUntil.isBefore(startDate.toLocalDate())) {
                return false;
            }
            else if (availableUntil.isEqual(endDate.toLocalDate()) || availableUntil.isAfter(endDate.toLocalDate())) {
                adjustedStartDate = startDate;
                adjustedEndDate = endDate;
            }
            else {
                adjustedStartDate = startDate;
                adjustedEndDate = availableUntil.atStartOfDay();
            }
        }
        else if (availableUntil == null) {
            if (availableAfter.isAfter(endDate.toLocalDate())) {
                return false;
            }
            else if (availableAfter.isEqual(startDate.toLocalDate()) || availableAfter.isBefore(startDate.toLocalDate())) {
                adjustedStartDate = startDate;
                adjustedEndDate = endDate;
            }
            else {
                adjustedStartDate = availableAfter.atStartOfDay();
                adjustedEndDate = endDate;
            }
        }
        else {
            if (availableUntil.isAfter(endDate.toLocalDate())) {
                if (availableAfter.isAfter(endDate.toLocalDate())) return false;
                adjustedEndDate = endDate;
            }
            else if (availableUntil.isEqual(startDate.toLocalDate()) || availableUntil.isAfter(startDate.toLocalDate())) {
                adjustedEndDate = availableUntil.atStartOfDay();
            }
            else {
                if (availableAfter.isBefore(availableUntil)) {
                    return false;
                }
                else if (availableAfter.isAfter(endDate.toLocalDate())) {
                    return false;
                }
                else if (availableAfter.isBefore(startDate.toLocalDate())) {
                    adjustedStartDate = startDate;
                    adjustedEndDate = endDate;
                }
                else {
                    adjustedStartDate = availableAfter.atStartOfDay();
                    adjustedEndDate = endDate;
                }
            }

            if (availableAfter.isBefore(startDate.toLocalDate())) {
                if (availableUntil.isBefore(startDate.toLocalDate())) return false;
                adjustedStartDate = startDate;
            }
            else if (availableAfter.isEqual(endDate.toLocalDate()) || availableAfter.isBefore(endDate.toLocalDate())) {
                adjustedStartDate = availableAfter.atStartOfDay();
            }
            else {
                if (availableAfter.isBefore(availableUntil)) {
                    return false;
                }
                else if (availableUntil.isBefore(startDate.toLocalDate())) {
                    return false;
                }
                else if (availableUntil.isAfter(endDate.toLocalDate())) {
                    adjustedStartDate = startDate;
                    adjustedEndDate = endDate;
                }
                else {
                    adjustedStartDate = startDate;
                    adjustedEndDate = availableUntil.atStartOfDay();
                }
            }

            if (availableUntil.isAfter(startDate.toLocalDate().minus(1, ChronoUnit.DAYS))
                    && availableUntil.isBefore(endDate.toLocalDate().plus(1, ChronoUnit.DAYS)) &&
                    availableAfter.isAfter(startDate.toLocalDate().minus(1, ChronoUnit.DAYS))
                    && availableAfter.isBefore(endDate.toLocalDate().plus(1, ChronoUnit.DAYS))
                    && availableUntil.isBefore(availableAfter)) {
                adjustedStartDate = startDate;
                adjustedEndDate = endDate;
            }
        }

        if (adjustedStartDate.isAfter(adjustedEndDate)) return false;


        List<Reservation> reservations = reservationRepository
                .getResortReservationsForRange(boatAd.getId(), adjustedStartDate, adjustedEndDate)
                .stream().filter(reservation -> !reservation.getCancelled()).toList();

        if (reservations.size() == 0) {
            return true;
        }
        if ((adjustedStartDate.isBefore(reservations.get(0).getStartDateTime()) &&
                adjustedStartDate.getDayOfYear() != reservations.get(0).getStartDateTime().getDayOfYear()) ||
                reservations.get(reservations.size()-1).getEndDateTime().isBefore(adjustedEndDate) ||
                reservations.get(reservations.size()-1).getEndDateTime().getDayOfYear() == adjustedEndDate.getDayOfYear()) {
            return true;
        }
        if (!reservations.get(0).getStartDateTime().isAfter(adjustedStartDate) &&
                reservations.get(0).getEndDateTime().isAfter(adjustedEndDate)) {
            return false;
        }
        for (int i = 0; i < reservations.size() - 1; i++) {
            if (reservations.get(i).getEndDateTime().getDayOfYear() !=
                    reservations.get(i+1).getStartDateTime().getDayOfYear()) {
                return true;
            }
        }
        return false;
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
}
