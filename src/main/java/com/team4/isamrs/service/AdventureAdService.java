package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.display.AdventureAdSimpleDisplayDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.model.advertisement.AdventureAd;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.AdventureAdRepository;
import com.team4.isamrs.repository.FishingEquipmentRepository;
import com.team4.isamrs.repository.ReservationRepository;
import com.team4.isamrs.repository.TagRepository;
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
public class AdventureAdService {

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAll(Class<T> returnType) {
        return adventureAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }


    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        AdventureAd adventureAd = adventureAdRepository.findById(id).orElseThrow();

        return modelMapper.map(adventureAd, returnType);
    }

    public Collection<AdventureAdSimpleDisplayDTO> findTopSix() {
        return adventureAdRepository.findAll(PageRequest.of(0, 6)).stream()
                .map(e -> modelMapper.map(e, AdventureAdSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public Page<AdventureAdSimpleDisplayDTO> search(String where, int guests, LocalDateTime startDate,
                                                    LocalDateTime endDate, Pageable pageable,
                                                    String sorting, boolean descending) {
        List<AdventureAdSimpleDisplayDTO> finalAdventures;
        List<AdventureAd> candidateAdventureAds = adventureAdRepository.search(where, guests);
        finalAdventures = candidateAdventureAds.stream()
                .filter(a -> isAvailable(a, startDate, endDate))
                .map(a -> modelMapper.map(a, AdventureAdSimpleDisplayDTO.class))
                .collect(Collectors.toList());

        if (sorting.equals("price"))
            finalAdventures.sort(Comparator.comparing(AdventureAdSimpleDisplayDTO::getPricePerPerson));
        // else sort by average rating
        if (descending) Collections.reverse(finalAdventures);

        int fromIndex = Math.min((int)pageable.getOffset(), finalAdventures.size());
        int toIndex = Math.min((fromIndex + pageable.getPageSize()), finalAdventures.size());

        return new PageImpl<AdventureAdSimpleDisplayDTO>
                (finalAdventures.subList(fromIndex, toIndex), pageable, finalAdventures.size());

    }

    private boolean isAvailable(AdventureAd adventureAd, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null && endDate == null) return true;
        if (startDate == null) startDate = LocalDateTime.now();
        if (endDate == null) endDate = LocalDateTime.parse("2200-01-01T00:00");
        LocalDateTime adjustedStartDate, adjustedEndDate;
        LocalDate availableUntil = adventureAd.getAvailableUntil();
        LocalDate availableAfter = adventureAd.getAvailableAfter();

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
                .getResortReservationsForRange(adventureAd.getId(), adjustedStartDate, adjustedEndDate)
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
        for (int i = 0; i < reservations.size() - 1; i++) {
            if (Math.abs(reservations.get(i+1).getStartDateTime().getDayOfYear() -
                    reservations.get(i).getEndDateTime().getDayOfYear()) > 1) {
                return true;
            }
        }
        return false;
    }

    public AdventureAd create(AdventureAdCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();

        AdventureAd adventureAd = modelMapper.map(dto, AdventureAd.class);

        adventureAd.setAdvertiser(advertiser);
        adventureAd.verifyPhotosOwnership(advertiser);

        adventureAdRepository.save(adventureAd);
        tagRepository.saveAll(adventureAd.getTags());
        fishingEquipmentRepository.saveAll(adventureAd.getFishingEquipment());
        return adventureAd;
    }

    public void update(Long id, AdventureAdUpdationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        AdventureAd adventureAd = adventureAdRepository.findAdventureAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        modelMapper.map(dto, adventureAd);

        adventureAd.verifyPhotosOwnership(advertiser);

        fishingEquipmentRepository.saveAll(adventureAd.getFishingEquipment());
        tagRepository.saveAll(adventureAd.getTags());
        adventureAdRepository.save(adventureAd);
    }

    public void delete(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        AdventureAd adventureAd = adventureAdRepository.findAdventureAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        adventureAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(adventureAd)); // there must be a better solution
        adventureAd.getTags().forEach(e -> e.getAdvertisements().remove(adventureAd));

        adventureAdRepository.delete(adventureAd);
    }
}
