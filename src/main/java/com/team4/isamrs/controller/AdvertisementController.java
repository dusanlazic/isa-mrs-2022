package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseOK;
import com.team4.isamrs.dto.creation.ComplaintCreationDTO;
import com.team4.isamrs.dto.creation.ReservationCreationDTO;
import com.team4.isamrs.dto.creation.ReviewCreationDTO;
import com.team4.isamrs.dto.display.AverageRatingDisplayDTO;
import com.team4.isamrs.dto.display.QuickReservationSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ReviewPublicDisplayDTO;
import com.team4.isamrs.dto.updation.AvailabilityPeriodUpdationDTO;
import com.team4.isamrs.service.AdvertisementService;
import com.team4.isamrs.service.ComplaintService;
import com.team4.isamrs.service.ReservationService;
import com.team4.isamrs.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/ads",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReservationService reservationService;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK delete(@PathVariable Long id, Authentication auth) {
        advertisementService.delete(id, auth);
        return new ResponseOK("Advertisement deleted.");
    }

    @PutMapping(value = "/{id}/availability-period", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK updateAvailabilityPeriod(@PathVariable Long id, @Valid @RequestBody AvailabilityPeriodUpdationDTO dto, Authentication auth) {
        advertisementService.updateAvailabilityPeriod(id, dto, auth);
        return new ResponseOK("Availability period updated.");
    }

    @GetMapping(value="/{id}/unavailable-dates")
    public Collection<LocalDate> getUnavailableDates(@PathVariable Long id,
                                                     @RequestParam String year,
                                                     @RequestParam String month) {
        return advertisementService.getUnavailableDates(id, year, month);
    }

    @PostMapping(value = "/{id}/complaints", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseOK createComplaint(@PathVariable Long id, @Valid @RequestBody ComplaintCreationDTO dto, Authentication auth) {
        complaintService.create(id, dto, auth);
        return new ResponseOK("Complaint created.");
    }

    @GetMapping("/{id}/reviews")
    public Collection<ReviewPublicDisplayDTO> getReviews(@PathVariable Long id) {
        return advertisementService.getApprovedReviews(id);
    }

    @GetMapping(value = "/{id}/reviews/average")
    public AverageRatingDisplayDTO getAverageRating(@PathVariable Long id) {
        return advertisementService.getAverageRating(id);
    }

    @PostMapping(value = "/{id}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseOK createReview(@PathVariable Long id, @Valid @RequestBody ReviewCreationDTO dto, Authentication auth) {
        reviewService.create(id, dto, auth);
        return new ResponseOK("Review created.");
    }

    @PostMapping(value = "/{id}/reservations", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseOK createReportForReservation(@PathVariable Long id, @Valid @RequestBody ReservationCreationDTO dto, Authentication auth) {
        reservationService.create(id, dto, auth);
        return new ResponseOK("Reservation created.");
    }

    @PatchMapping(value = "/{id}/subscribe")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseOK subscribe(@PathVariable Long id, Authentication auth) {
        advertisementService.subscribe(id, auth);
        return new ResponseOK("Subscription added.");
    }

    @PatchMapping(value = "/{id}/unsubscribe")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseOK unsubscribe(@PathVariable Long id, Authentication auth) {
        advertisementService.unsubscribe(id, auth);
        return new ResponseOK("Subscription removed.");
    }

    @GetMapping(value = "/{id}/quick-reservations")
    public Collection<QuickReservationSimpleDisplayDTO> getQuickReservations(@PathVariable Long id) {
        return reservationService.getQuickReservations(id);
    }
}
