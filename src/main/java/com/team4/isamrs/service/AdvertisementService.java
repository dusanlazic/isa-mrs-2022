package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.AverageRatingDisplayDTO;
import com.team4.isamrs.dto.display.ReportItemDisplayDTO;
import com.team4.isamrs.dto.display.ReviewPublicDisplayDTO;
import com.team4.isamrs.dto.updation.AvailabilityPeriodUpdationDTO;
import com.team4.isamrs.exception.IdenticalAvailabilityDatesException;
import com.team4.isamrs.exception.ReservationsInUnavailabilityPeriodException;
import com.team4.isamrs.model.advertisement.AdventureAd;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.*;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void delete(Long id, Authentication auth) {
        /* Note:
        Do not allow deletion if any future or current reservations for this ad exist.
         */
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findAdvertisementByIdAndAdvertiser(id, advertiser).orElseThrow();

        // Update bidirectional relationships
        advertisement.getTags().forEach(e -> e.getAdvertisements().remove(advertisement));

        if (advertisement instanceof AdventureAd) {
            AdventureAd adventureAd = (AdventureAd) advertisement;
            adventureAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(advertisement));
        } else if (advertisement instanceof BoatAd) {
            BoatAd boatAd = (BoatAd) advertisement;
            boatAd.getNavigationalEquipment().forEach(e -> e.getAdvertisements().remove(advertisement));
            boatAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(advertisement));
        }

        advertisementRepository.delete(advertisement);
    }

    public void subscribe(Long id, Authentication auth) {
        Customer customer = (Customer) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();
        Set<Advertisement> currentSubscriptions = customerRepository.getSubscriptionsByCustomer(customer);
        currentSubscriptions.add(advertisement);
        customer.setSubscriptions(currentSubscriptions);
        customerRepository.save(customer);
    }

    public void unsubscribe(Long id, Authentication auth) {
        Customer customer = (Customer) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();
        Set<Advertisement> currentSubscriptions = customerRepository.getSubscriptionsByCustomer(customer);
        currentSubscriptions.remove(advertisement);
        customer.setSubscriptions(currentSubscriptions);
        customerRepository.save(customer);
    }

    public Collection<LocalDate> getUnavailableDates(Long id, String year, String month) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();
        LocalDate startDate = LocalDate.of(Integer.parseInt(year), Month.valueOf(month).ordinal() + 1,1);
        LocalDate endDate = LocalDate.of(Integer.parseInt(year), Month.valueOf(month).ordinal() + 1,
                Month.valueOf(month).length(Integer.parseInt(year) % 4 == 0));

        Set<LocalDate> unavailableDates = new HashSet<>();
        // if startDate and endDate are same, it still returns one date (start)
        startDate.datesUntil(endDate.plusDays(1)).forEach(date -> {
            if (!isWithinAvailabilityPeriod(advertisement, date)) unavailableDates.add(date);
        });

        advertisement.getReservations()
                .stream()
                .filter(reservation -> !reservation.getCancelled() &&
                        (reservation.getStartDateTime().getMonth() == Month.valueOf(month) ||
                        reservation.getEndDateTime().getMonth() == Month.valueOf(month)))
                .forEach(reservation -> {
                    if (reservation.getStartDateTime().getDayOfYear() == reservation.getEndDateTime().getDayOfYear()) {
                        unavailableDates.add(reservation.getStartDateTime().toLocalDate());
                    }
                    else {
                        reservation.getStartDateTime().toLocalDate()
                                .datesUntil(reservation.getEndDateTime().toLocalDate())
                                .forEach(unavailableDates::add);
                    }
                });

        quickReservationRepository.findUnexpiredUntakenQuickReservations(advertisement, LocalDateTime.now())
                .stream()
                .filter(quickReservation ->
                        (quickReservation.getStartDateTime().getMonth() == Month.valueOf(month) ||
                                quickReservation.getEndDateTime().getMonth() == Month.valueOf(month)))
                .forEach(quickReservation -> {
                    if (quickReservation.getStartDateTime().getDayOfYear() == quickReservation.getEndDateTime().getDayOfYear()) {
                        unavailableDates.add(quickReservation.getStartDateTime().toLocalDate());
                    }
                    else {
                        quickReservation.getStartDateTime().toLocalDate()
                                .datesUntil(quickReservation.getEndDateTime().toLocalDate())
                                .forEach(unavailableDates::add);
                    }
                });

        return unavailableDates;
    }

    private boolean isWithinAvailabilityPeriod(Advertisement ad, LocalDate date) {
        LocalDate availableAfter = ad.getAvailableAfter();
        LocalDate availableUntil = ad.getAvailableUntil();
        if (availableAfter == null && availableUntil == null) return false;
        else if (availableAfter != null && availableUntil == null) {
            return date.isAfter(availableAfter);
        }
        else if (availableAfter == null) {
            return date.isBefore(availableUntil);
        }
        else {
            if (availableAfter.isBefore(availableUntil))
                return date.isAfter(availableAfter) && date.isBefore(availableUntil);
            else if (availableUntil.isBefore(availableAfter))
                return date.isBefore(availableUntil) || date.isAfter(availableAfter);
            else
                return !date.isEqual(availableAfter);
        }
    }

    public AverageRatingDisplayDTO getAverageRating(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();
        Double value = advertisementRepository.findAverageRatingForAdvertisement(advertisement).orElse(0.0);

        return new AverageRatingDisplayDTO(Math.round(value * 100.0) / 100.0);
    }

    public Collection<ReviewPublicDisplayDTO> getApprovedReviews(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();
        return advertisement.getReviews().stream()
                .filter(e -> e.getApprovalStatus().equals(ApprovalStatus.APPROVED))
                .map(e -> modelMapper.map(e, ReviewPublicDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public void updateAvailabilityPeriod(Long id, AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        if (dto.getAvailableUntil() != null && dto.getAvailableAfter() != null &&
                dto.getAvailableUntil().equals(dto.getAvailableAfter()))
            throw new IdenticalAvailabilityDatesException();

        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findAdvertisementByIdAndAdvertiser(id, advertiser).orElseThrow();

        if (!(dto.getAvailableUntil() == null && dto.getAvailableAfter() == null)) {
            // only available until is defined
            if (dto.getAvailableAfter() == null) {
                Set<Reservation> reservations = reservationRepository.findReservationsByEndDateTimeAfterAndCancelledIsFalse(dto.getAvailableUntil().atStartOfDay());
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // only available after is defined
            else if (dto.getAvailableUntil() == null) {
                Set<Reservation> reservations = reservationRepository.findReservationsByStartDateTimeBeforeAndCancelledIsFalse(dto.getAvailableAfter().atStartOfDay());
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // both are defined
            // availability period
            else if (dto.getAvailableAfter().isBefore(dto.getAvailableUntil())) {
                Set<Reservation> reservations = reservationRepository.findReservationsByStartDateTimeBeforeOrEndDateTimeAfterAndCancelledIsFalse(dto.getAvailableAfter().atStartOfDay(), dto.getAvailableUntil().atStartOfDay());
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
            // unavailability period
            else {
                Set<Reservation> reservations = reservationRepository.findReservationsByStartDateTimeBeforeAndEndDateTimeAfterAndCancelledIsFalse(dto.getAvailableAfter().atStartOfDay(), dto.getAvailableUntil().atStartOfDay());
                if (!reservations.isEmpty())
                    throw new ReservationsInUnavailabilityPeriodException(Integer.toString(reservations.size()));
            }
        }

        advertisement.setAvailableAfter(dto.getAvailableAfter());
        advertisement.setAvailableUntil(dto.getAvailableUntil());
        advertisementRepository.save(advertisement);
    }

    public Collection<ReportItemDisplayDTO> getMonthlyReport(Long id) {
        List<ReportItemDisplayDTO> res = new ArrayList<>();

        LocalDateTime firstDate = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime lastDate = LocalDateTime.now();

        List<Reservation> reservations = reservationRepository.getResortReservationsForRange(id, firstDate, lastDate).stream().toList();
        ReportItemDisplayDTO item = new ReportItemDisplayDTO();
        item.setName(lastDate.getMonth().getDisplayName(TextStyle.FULL, Locale.UK));
        item.setValue(reservations.size());
        res.add(item);

        for (int i = 0; i < 6; i++) {
            lastDate = firstDate;
            firstDate = firstDate.minusMonths(1);

            reservations = reservationRepository.getResortReservationsForRange(id, firstDate, lastDate).stream().toList();
            item = new ReportItemDisplayDTO();
            item.setName(lastDate.getMonth().getDisplayName(TextStyle.FULL, Locale.UK));
            item.setValue(reservations.size());
            res.add(item);
        }
        return res;
    }

    public Collection<ReportItemDisplayDTO> getYearlyReport(Long id) {
        List<ReportItemDisplayDTO> res = new ArrayList<>();

        LocalDateTime firstDate = LocalDateTime.now().withDayOfYear(1);
        LocalDateTime lastDate = LocalDateTime.now();

        List<Reservation> reservations = reservationRepository.getResortReservationsForRange(id, firstDate, lastDate).stream().toList();
        ReportItemDisplayDTO item = new ReportItemDisplayDTO();
        item.setName(String.valueOf(lastDate.getYear()));
        item.setValue(reservations.size());
        res.add(item);

        for (int i = 0; i < 6; i++) {
            lastDate = firstDate;
            firstDate = firstDate.minusYears(1);

            reservations = reservationRepository.getResortReservationsForRange(id, firstDate, lastDate).stream().toList();
            item = new ReportItemDisplayDTO();
            item.setName(String.valueOf(lastDate.getYear()));
            item.setValue(reservations.size());
            res.add(item);
        }
        return res;
    }

    public Collection<ReportItemDisplayDTO> getWeeklyReport(Long id) {
        List<ReportItemDisplayDTO> res = new ArrayList<>();

        LocalDateTime lastDate = LocalDateTime.now();
        LocalDateTime firstDate = LocalDateTime.now();

        List<Reservation> reservations;
        ReportItemDisplayDTO item;

        for (int i = 0; i < 7; i++) {
            lastDate = firstDate;
            firstDate = firstDate.minusWeeks(1);

            reservations = reservationRepository.getResortReservationsForRange(id, firstDate, lastDate).stream().toList();
            item = new ReportItemDisplayDTO();
            item.setName(lastDate.getMonth().getDisplayName(TextStyle.FULL, Locale.UK));
            item.setValue(reservations.size());
            res.add(item);
        }
        return res;
    }
}
