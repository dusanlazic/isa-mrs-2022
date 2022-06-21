package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ResortAdCreationDTO;
import com.team4.isamrs.dto.display.ResortAdDisplayDTO;
import com.team4.isamrs.dto.display.ResortAdSimpleDisplayDTO;
import com.team4.isamrs.dto.updation.ResortAdUpdationDTO;
import com.team4.isamrs.exception.ReservationConflictException;
import com.team4.isamrs.exception.ReservationPeriodUnavailableException;
import com.team4.isamrs.model.advertisement.AdventureAd;
import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.advertisement.ResortAd;
import com.team4.isamrs.model.reservation.QuickReservation;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.QuickReservationRepository;
import com.team4.isamrs.repository.ReservationRepository;
import com.team4.isamrs.repository.ResortAdRepository;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
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
public class ResortAdService {

    @Autowired
    private ResortAdRepository resortAdRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    public Page<ResortAdSimpleDisplayDTO> search(String where, int guests, LocalDateTime startDate,
                                                 LocalDateTime endDate, Pageable pageable,
                                                 String sorting, boolean descending) {
        List<ResortAdSimpleDisplayDTO> finalResorts;
        List<ResortAd> candidateResortAds = resortAdRepository.search(where, guests);
        finalResorts = candidateResortAds.stream()
                .filter(r -> isAvailable(r, startDate, endDate))
                .map(r -> modelMapper.map(r, ResortAdSimpleDisplayDTO.class))
                .collect(Collectors.toList());

        if (sorting.equals("price"))
            finalResorts.sort(Comparator.comparing(ResortAdSimpleDisplayDTO::getPricePerDay));
        // else sort by average rating
        if (descending) Collections.reverse(finalResorts);

        int fromIndex = Math.min((int)pageable.getOffset(), finalResorts.size());
        int toIndex = Math.min((fromIndex + pageable.getPageSize()), finalResorts.size());

        return new PageImpl<ResortAdSimpleDisplayDTO>
                (finalResorts.subList(fromIndex, toIndex), pageable, finalResorts.size());

    }

    private boolean isAvailable(ResortAd resortAd, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null && endDate == null) return true;
        if (startDate == null) startDate = LocalDateTime.now();
        if (endDate == null) endDate = LocalDateTime.parse("2200-01-01T00:00");
        LocalDateTime adjustedStartDate, adjustedEndDate;
        LocalDate availableUntil = resortAd.getAvailableUntil();
        LocalDate availableAfter = resortAd.getAvailableAfter();

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
                .getResortReservationsForRange(resortAd.getId(), adjustedStartDate, adjustedEndDate)
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
        ResortAd resortAd = resortAdRepository.findResortAdByIdAndAdvertiser(id, advertiser).orElseThrow();
        try {
            resortAd = resortAdRepository.lockFindResortAdByIdAndAdvertiser(id, advertiser).orElseThrow();
        }
        catch (PessimisticLockingFailureException e) {
            throw new ReservationConflictException("Another user is currently attempting to make a reservation" +
                    " for the same entity. Please try again in a few seconds.");
        }
        modelMapper.map(dto, resortAd);

        resortAd.verifyPhotosOwnership(advertiser);

        tagRepository.saveAll(resortAd.getTags());
        resortAdRepository.save(resortAd);
    }
}
