package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.QuickReservationCreationDTO;
import com.team4.isamrs.dto.creation.ReservationCreationDTO;
import com.team4.isamrs.dto.display.QuickReservationSimpleDisplayDTO;
import com.team4.isamrs.dto.creation.ReservationRenewalCreationDTO;
import com.team4.isamrs.dto.display.ReservationDetailedDisplayDTO;
import com.team4.isamrs.dto.display.ReservationSimpleDisplayDTO;
import com.team4.isamrs.exception.*;
import com.team4.isamrs.model.advertisement.*;
import com.team4.isamrs.model.loyalty.LoyaltyProgramCategory;
import com.team4.isamrs.model.loyalty.TargetedAccountType;
import com.team4.isamrs.model.reservation.QuickReservation;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.model.user.User;
import com.team4.isamrs.repository.*;
import com.team4.isamrs.security.EmailSender;
import org.apache.tika.utils.StringUtils;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
    private CustomerRepository customerRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ResortAdRepository resortAdRepository;

    @Autowired
    private BoatAdRepository boatAdRepository;

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    @Autowired
    private LoyaltyProgramCategoryRepository loyaltyProgramCategoryRepository;

    @Autowired
    private QuickReservationRepository quickReservationRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private ModelMapper modelMapper;

    public void cancel(Long id, Authentication auth) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        if (LocalDateTime.now().until(reservation.getStartDateTime(), ChronoUnit.HOURS) < 72)
            throw new ReservationCancellationWithinThreeDaysException("Unable to cancel reservation. It starts within three days.");
        reservation.setCancelled(true);
        reservationRepository.save(reservation);
    }

    public ReservationSimpleDisplayDTO findById(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        if (!reservation.getAdvertisement().getAdvertiser().getUsername().equals(advertiser.getUsername()))
            throw new NoSuchElementException();

        return modelMapper.map(reservation, ReservationSimpleDisplayDTO.class);
    }

    public ReservationDetailedDisplayDTO findDetailedById(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        if (!reservation.getAdvertisement().getAdvertiser().getUsername().equals(advertiser.getUsername()))
            throw new NoSuchElementException();
        Advertisement advertisement = (Advertisement) Hibernate.unproxy(reservation.getAdvertisement());
        ReservationDetailedDisplayDTO dto = modelMapper.map(reservation, ReservationDetailedDisplayDTO.class);
        dto.setType(advertisement instanceof ResortAd ? "resort" :
                advertisement instanceof BoatAd ? "boat" : "adventure");
        // dto.setAttendees(reservation.getAttendees());
        return dto;
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

    public Collection<QuickReservationSimpleDisplayDTO> getQuickReservations(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();
        return quickReservationRepository.findActiveUntakenQuickReservations(
                advertisement, LocalDateTime.now())
                .stream()
                .filter(qr -> qr.getReservation() == null || qr.getReservation().getCancelled())
                .map(qr -> modelMapper.map(qr, QuickReservationSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
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

    @Transactional
    public void bookQuickReservation(Long id, Authentication auth) {
        Customer customer = (Customer) auth.getPrincipal();
        QuickReservation quickReservation;
        try {
            quickReservation = quickReservationRepository.lockGetById(id).orElseThrow();
        }
        catch (PessimisticLockingFailureException e) {
            throw new ReservationConflictException(
                    "Another user is currently attempting to book this quick reservation. " +
                    "Please try again in a few seconds.");
        }
        if (quickReservation.getReservation() != null && !quickReservation.getReservation().getCancelled()) {
            throw new QuickReservationAlreadyBookedException(
                    "The quick reservation was already booked by another customer.");
        }
        if (quickReservation.getValidUntil().isBefore(LocalDateTime.now())) {
            throw new QuickReservationInvalidException(
                    "The quick reservation has expired.");
        }
        if (quickReservation.getValidAfter().isAfter(LocalDateTime.now())) {
            throw new QuickReservationInvalidException(
                    "The quick reservation is yet to become available for booking.");
        }
        if (customer.getPenalties() >= 3) {
            throw new TooManyPenaltiesException("Since the number of penalties you have received this month is " +
                    customer.getPenalties() + ", you are prohibited from making reservations until the beginning " +
                    "of next month.");
        }

        if (reservationRepository.findByAdvertisementEqualsAndCustomerEqualsAndCancelledIsTrue(
                quickReservation.getAdvertisement(), customer)
                .stream().anyMatch(reservation ->
                        reservation.getStartDateTime().toLocalDate().equals(quickReservation.getStartDateTime().toLocalDate()) &&
                                reservation.getEndDateTime().toLocalDate().equals(quickReservation.getEndDateTime().toLocalDate()))) {
            throw new ReservationPeriodUnavailableException("Attempted re-reservation of same entity during" +
                    " the same period after cancelling an identical reservation.");
        }

        Reservation reservation = new Reservation();
        reservation.setCancelled(false);
        reservation.setStartDateTime(quickReservation.getStartDateTime());
        reservation.setEndDateTime(quickReservation.getEndDateTime());
        reservation.setCustomer(customer);
        reservation.setCalculatedPrice(quickReservation.getNewPrice());
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setAdvertisement(quickReservation.getAdvertisement());
        reservation.setAttendees(quickReservation.getCapacity());
        quickReservation.setReservation(reservation);
        reservation.setSelectedOptions(quickReservation.getSelectedOptions()
        .stream().map(selectedOption -> {
            SelectedOption newSo = new SelectedOption();
            newSo.setCount(selectedOption.getCount());
            newSo.setOption(selectedOption.getOption());
            return newSo;
        }).collect(Collectors.toSet()));

        emailSender.sendReservationConfirmationEmail(reservation, reservation.getAdvertisement().getPhotos().get(0));
        reservationRepository.save(reservation);
        quickReservationRepository.save(quickReservation);
    }

    @Transactional
    public void create(Long id, ReservationCreationDTO dto, Authentication auth) {
        Customer customer = (Customer) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();

        try {
            if (advertisement instanceof ResortAd) {
                advertisement = resortAdRepository.lockGetById(id).orElseThrow();
                dto.setEndDate(dto.getEndDate().plusDays(1));
            }
            else if (advertisement instanceof BoatAd) {
                advertisement = boatAdRepository.lockGetById(id).orElseThrow();
                dto.setEndDate(dto.getEndDate().plusDays(1));
            }
            else if (advertisement instanceof AdventureAd) {
                advertisement = adventureAdRepository.lockGetById(id).orElseThrow();
                if (!dto.getStartDate().isEqual(dto.getEndDate()))
                    throw new ReservationPeriodUnavailableException("Adventure reservation must be within one day.");
            }
        }
        catch (PessimisticLockingFailureException e) {
            throw new ReservationConflictException("Another user is currently attempting to make a reservation" +
                    " for the same entity. Please try again in a few seconds.");
        }

        if (customer.getPenalties() >= 3) {
            throw new TooManyPenaltiesException("Since the number of penalties you have received this month is " +
                    customer.getPenalties() + ", you are prohibited from making reservations until the beginning " +
                    "of next month.");
        }

        if (reservationRepository.findByAdvertisementEqualsAndCustomerEqualsAndCancelledIsTrue(advertisement, customer)
                .stream().anyMatch(reservation ->
                reservation.getStartDateTime().toLocalDate().equals(dto.getStartDate()) &&
                reservation.getEndDateTime().toLocalDate().equals(dto.getEndDate()))) {
            throw new ReservationPeriodUnavailableException("Attempted re-reservation of same entity during" +
                    " the same period after cancelling an identical reservation.");
        }

        if (dto.getEndDate().isBefore(dto.getStartDate()))
            throw new EndDateBeforeStartDateException("End date cannot be before start date.");

        if (advertisement.getCapacity() < dto.getAttendees())
            throw new ExceededMaxAttendeesException("Exceeded maximum number of attendees (" +
                    advertisement.getCapacity() + ")");

        if (!isAvailableForReservation(advertisement, dto.getStartDate(), dto.getEndDate()))
            throw new ReservationPeriodUnavailableException("Unable to make reservation. Requested period is unavailable.");

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setAdvertisement(advertisement);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setStartDateTime(dto.getStartDate().atTime(advertisement.getCheckInTime()));
        reservation.setEndDateTime(dto.getEndDate().atTime(advertisement.getCheckOutTime()));
        reservation.setCancelled(false);
        reservation.setSelectedOptions(generateSelectedOptions(dto, advertisement));
        reservation.setCalculatedPrice(calculateReservationPrice(dto, advertisement, customer));
        reservation.setAttendees(dto.getAttendees());

        emailSender.sendReservationConfirmationEmail(reservation, reservation.getAdvertisement().getPhotos().get(0));
        reservationRepository.save(reservation);
    }

    private Set<SelectedOption> generateSelectedOptions(ReservationCreationDTO dto, Advertisement advertisement) {
        return dto.getSelectedOptions()
                .stream()
                .map(selectedOptionDto -> {
                    Option option = advertisement
                            .getOptions()
                            .stream()
                            .filter(x -> x.getId().equals(selectedOptionDto.getOptionId()))
                            .findFirst()
                            .orElseThrow();
                    if (selectedOptionDto.getCount() > option.getMaxCount()) {
                        throw new ExceededOptionMaxCountValueException(
                                "Selected value (" + selectedOptionDto.getCount() + ") for " + option.getName() +
                                        " exceeds max value of " + option.getMaxCount());
                    }
                    SelectedOption selectedOption = new SelectedOption();
                    selectedOption.setOption(option);
                    selectedOption.setCount(selectedOptionDto.getCount());
                    return selectedOption;
                })
                .collect(Collectors.toSet());
    }

    private Set<SelectedOption> generateSelectedOptions(QuickReservationCreationDTO dto, Advertisement advertisement) {
        return dto.getSelectedOptions()
                .stream()
                .map(selectedOptionDto -> {
                    Option option = advertisement
                            .getOptions()
                            .stream()
                            .filter(x -> x.getId().equals(selectedOptionDto.getOptionId()))
                            .findFirst()
                            .orElseThrow();
                    if (selectedOptionDto.getCount() > option.getMaxCount()) {
                        throw new ExceededOptionMaxCountValueException(
                                "Selected value (" + selectedOptionDto.getCount() + ") for " + option.getName() +
                                        " exceeds max value of " + option.getMaxCount());
                    }
                    SelectedOption selectedOption = new SelectedOption();
                    selectedOption.setOption(option);
                    selectedOption.setCount(selectedOptionDto.getCount());
                    return selectedOption;
                })
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateReservationPrice(ReservationCreationDTO dto, Advertisement advertisement,
                                                 Customer customer) {
        LoyaltyProgramCategory category = loyaltyProgramCategoryRepository
                .findByPointsAndAccountType(customer.getPoints(), TargetedAccountType.CUSTOMER).orElseThrow();
        BigDecimal pricePerThing = new BigDecimal(0);
        if (advertisement instanceof ResortAd) {
            ResortAd ad = (ResortAd) advertisement;
            pricePerThing = ad.getPricePerDay();
        }
        else if (advertisement instanceof BoatAd) {
            BoatAd ad = (BoatAd) advertisement;
            pricePerThing = ad.getPricePerDay();
        }
        else if (advertisement instanceof AdventureAd){
            AdventureAd ad = (AdventureAd) advertisement;
            pricePerThing = ad.getPricePerPerson();
        }
        long thing = advertisement instanceof AdventureAd ? dto.getAttendees()
                : dto.getStartDate().until(dto.getEndDate(), ChronoUnit.DAYS);
        return BigDecimal.valueOf(thing)
                .multiply(pricePerThing)
                .multiply(category.getMultiply())
                .setScale(2, RoundingMode.UP);
    }

    private BigDecimal calculateReservationPrice(QuickReservationCreationDTO dto, Advertisement advertisement) {
        BigDecimal pricePerThing = new BigDecimal(0);
        if (advertisement instanceof ResortAd) {
            ResortAd ad = (ResortAd) advertisement;
            pricePerThing = ad.getPricePerDay();
        }
        else if (advertisement instanceof BoatAd) {
            BoatAd ad = (BoatAd) advertisement;
            pricePerThing = ad.getPricePerDay();
        }
        else if (advertisement instanceof AdventureAd){
            AdventureAd ad = (AdventureAd) advertisement;
            pricePerThing = ad.getPricePerPerson();
        }
        long thing = advertisement instanceof AdventureAd ? dto.getCapacity()
                : dto.getStartDate().until(dto.getEndDate(), ChronoUnit.DAYS);
        return BigDecimal.valueOf(thing)
                .multiply(pricePerThing)
                .setScale(2, RoundingMode.UP);
    }

    public boolean isAvailableForReservation(Advertisement advertisement, LocalDate startDate, LocalDate endDate) {

        Set<LocalDate> unavailableDates = new HashSet<LocalDate>();

        if (startDate.isBefore(LocalDate.now())) return false;

        startDate.datesUntil(endDate).forEach(date -> {
            if (!isWithinAvailabilityPeriod(advertisement, date)) unavailableDates.add(date);
        });

        advertisement.getReservations()
                .stream()
                .filter(reservation -> !reservation.getCancelled())
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

        return startDate
                .datesUntil(endDate)
                .filter(date -> {
                    for (LocalDate d : unavailableDates) {
                        if (d.equals(date)) return true;
                    }
                    return false;
                        }).toList().size() == 0;
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
    @Transactional
    public void createQuickReservation(QuickReservationCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findAdvertisementByIdAndAdvertiser(dto.getAdvertisementId(), advertiser).orElseThrow();

        try {
            if (advertisement instanceof ResortAd) {
                advertisement = resortAdRepository.lockGetById(dto.getAdvertisementId()).orElseThrow();
                dto.setEndDate(dto.getEndDate().plusDays(1));
            }
            else if (advertisement instanceof BoatAd) {
                advertisement = boatAdRepository.lockGetById(dto.getAdvertisementId()).orElseThrow();
                dto.setEndDate(dto.getEndDate().plusDays(1));
            }
            else if (advertisement instanceof AdventureAd) {
                advertisement = adventureAdRepository.lockGetById(dto.getAdvertisementId()).orElseThrow();
                if (!dto.getStartDate().isEqual(dto.getEndDate()))
                    throw new ReservationPeriodUnavailableException("Adventure reservation must be within one day.");
            }
        }
        catch (PessimisticLockingFailureException e) {
            throw new ReservationConflictException("Customer is currently attempting to make a reservation" +
                    " for the same entity. Please try again in a few seconds.");
        }

        if (dto.getEndDate().isBefore(LocalDate.now()) || dto.getStartDate().isBefore(LocalDate.now()) ||
                dto.getValidUntil().isBefore(LocalDate.now()) || dto.getValidAfter().isBefore(LocalDate.now()))
            throw new EndDateBeforeStartDateException("Dates cannot be in the past.");

        if (dto.getEndDate().isBefore(dto.getStartDate()))
            throw new EndDateBeforeStartDateException("End date cannot be before start date.");

        if (dto.getValidUntil().isBefore(dto.getValidAfter()))
            throw new EndDateBeforeStartDateException("Valid until cannot be before valid after.");

        if (dto.getStartDate().isBefore(dto.getValidUntil()))
            throw new EndDateBeforeStartDateException("Start date cannot be before valid until.");

        if (advertisement.getCapacity() < dto.getCapacity())
            throw new ExceededMaxAttendeesException("Exceeded maximum number of attendees (" +
                    advertisement.getCapacity() + ")");

        if (calculateReservationPrice(dto, advertisement).compareTo(dto.getNewPrice()) < 0)
            throw new DiscountCostsMoreThanOriginalException("Discount (" + dto.getNewPrice() + ") cannot cost" +
                    "more than the original price of " + calculateReservationPrice(dto, advertisement) + ". [" +
                    advertisement.getCurrency() + "]");

        if (!isAvailableForReservation(advertisement, dto.getStartDate(), dto.getEndDate()))
            throw new ReservationPeriodUnavailableException("Unable to make reservation. Requested period is unavailable.");

        QuickReservation quickReservation = new QuickReservation();
        quickReservation.setAdvertisement(advertisement);
        quickReservation.setCreatedAt(LocalDateTime.now());
        quickReservation.setSelectedOptions(generateSelectedOptions(dto, advertisement));
        quickReservation.setValidAfter(dto.getValidAfter().atStartOfDay());
        quickReservation.setValidUntil(dto.getValidUntil().atStartOfDay());
        quickReservation.setCalculatedOldPrice(calculateReservationPrice(dto, advertisement));
        quickReservation.setNewPrice(dto.getNewPrice());
        quickReservation.setStartDateTime(dto.getStartDate().atTime(advertisement.getCheckInTime()));
        quickReservation.setEndDateTime(dto.getEndDate().atTime(advertisement.getCheckOutTime()));
        quickReservation.setCapacity(dto.getCapacity());
        quickReservation.setReservation(null);

        quickReservationRepository.save(quickReservation);

        Collection<Customer> subscribers = advertisementRepository.getSubscribersOfAdvertisement(advertisement);
        emailSender.sendDiscountNotificationEmails(quickReservation, advertisement.getPhotos().get(0),
                subscribers);
    }

    @Transactional
    public void create(Long advertisementId, ReservationRenewalCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Reservation reservation = reservationRepository.findByAdvertisementIdAndAdvertiserIdAndClientIdAndDate(dto.getReservationId(), advertisementId, advertiser.getId(), dto.getCustomerId(), LocalDateTime.now()).orElseThrow();
        Advertisement advertisement = (Advertisement) Hibernate.unproxy(reservation.getAdvertisement());
        try {
            if (advertisement instanceof ResortAd) {
                advertisement = resortAdRepository.lockGetById(advertisementId).orElseThrow();
                dto.setEndDate(dto.getEndDate().plusDays(1));
            }
            else if (advertisement instanceof BoatAd) {
                advertisement = boatAdRepository.lockGetById(advertisementId).orElseThrow();
                dto.setEndDate(dto.getEndDate().plusDays(1));
            }
            else if (advertisement instanceof AdventureAd) {
                advertisement = adventureAdRepository.lockGetById(advertisementId).orElseThrow();
                if (!dto.getStartDate().isEqual(dto.getEndDate()))
                    throw new ReservationPeriodUnavailableException("Adventure reservation must be within one day.");
            }
        }
        catch (PessimisticLockingFailureException e) {
            throw new ReservationConflictException("Another user is currently attempting to make a reservation" +
                    " for the same entity. Please try again in a few seconds.");
        }

        if (reservationRepository.findByAdvertisementEqualsAndCustomerEqualsAndCancelledIsTrue(advertisement, reservation.getCustomer())
                .stream().anyMatch(r ->
                        r.getStartDateTime().toLocalDate().equals(dto.getStartDate()) &&
                                r.getEndDateTime().toLocalDate().equals(dto.getEndDate()))) {
            throw new ReservationPeriodUnavailableException("Attempted re-reservation of same entity during" +
                    " the same period after cancelling an identical reservation.");
        }

        if (dto.getEndDate().isBefore(dto.getStartDate()))
            throw new EndDateBeforeStartDateException("End date cannot be before start date.");

        if (advertisement.getCapacity() < dto.getAttendees())
            throw new ExceededMaxAttendeesException("Exceeded maximum number of attendees (" +
                    advertisement.getCapacity() + ")");

        if (!isAvailableForReservation(advertisement, dto.getStartDate(), dto.getEndDate()))
            throw new ReservationPeriodUnavailableException("Unable to make reservation. Requested period is unavailable.");

        Reservation newReservation = new Reservation();

        newReservation.setCustomer(reservation.getCustomer());
        newReservation.setAdvertisement(advertisement);
        newReservation.setCreatedAt(LocalDateTime.now());
        newReservation.setStartDateTime(dto.getStartDate().atTime(advertisement.getCheckInTime()));
        newReservation.setEndDateTime(dto.getEndDate().atTime(advertisement.getCheckOutTime()));
        newReservation.setCancelled(false);
        reservation.setSelectedOptions(generateSelectedOptions(modelMapper.map(dto, ReservationCreationDTO.class), advertisement));
        newReservation.setCalculatedPrice(calculateReservationPrice(modelMapper.map(dto, ReservationCreationDTO.class), advertisement, reservation.getCustomer()));

        emailSender.sendReservationConfirmationEmail(newReservation, newReservation.getAdvertisement().getPhotos().get(0));
        reservationRepository.save(newReservation);
    }
}
