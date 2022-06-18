package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseOK;
import com.team4.isamrs.dto.creation.QuickReservationCreationDTO;
import com.team4.isamrs.dto.creation.ReservationReportCreationDTO;
import com.team4.isamrs.dto.display.ReservationReportDisplayDTO;
import com.team4.isamrs.dto.display.ReservationSimpleDisplayDTO;
import com.team4.isamrs.service.ReservationReportService;
import com.team4.isamrs.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/reservations",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationReportService reservationReportService;

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public ReservationSimpleDisplayDTO find(@PathVariable Long id, Authentication auth) {
        return reservationService.findById(id, auth);
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public Page<ReservationSimpleDisplayDTO> findAll(@RequestParam int page, Authentication auth) {
        return reservationService.findAll(PageRequest.of(page, 25), auth);
    }

    @GetMapping(value = "/active", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public Page<ReservationSimpleDisplayDTO> findActive(@RequestParam int page, Authentication auth) {
        return reservationService.findActive(PageRequest.of(page, 25), auth);
    }

    @GetMapping(value = "/completed", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public Page<ReservationSimpleDisplayDTO> findCompleted(@RequestParam int page,
                                                           @RequestParam(required = false) boolean hasReport,
                                                           Authentication auth) {
        return reservationService.findCompleted(PageRequest.of(page, 25), hasReport, auth);
    }

    @GetMapping(value = "/{id}/report", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public ReservationReportDisplayDTO findReportForReservation(@PathVariable Long id, Authentication auth) {
        return reservationReportService.findForReservation(id, auth);
    }

    @PostMapping(value = "/{id}/report")
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK createReportForReservation(@PathVariable Long id, @Valid @RequestBody ReservationReportCreationDTO dto, Authentication auth) {
        reservationReportService.create(id, dto, auth);
        return new ResponseOK("Report created.");
    }

    @PatchMapping(value = "/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseOK cancelReservation(@PathVariable Long id, Authentication auth) {
        reservationService.cancel(id, auth);
        return new ResponseOK("Reservation cancelled.");
    }

    @PostMapping(value = "/quick-reservation")
    @PreAuthorize("hasRole('ADVERTISER')")
    public ResponseOK createQuickReservation(@Valid @RequestBody QuickReservationCreationDTO dto, Authentication auth) {
        reservationService.createQuickReservation(dto, auth);
        return new ResponseOK("Quick reservation created.");
    }
}
