package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.*;
import com.team4.isamrs.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping(value = "/advertisers",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdvertiserController {

    @Autowired
    private AdvertiserService advertiserService;

    @GetMapping(value = "/{id}")
    public AccountDisplayDTO getAdvertiser(@PathVariable Long id) {
        return advertiserService.findById(id);
    }

    @GetMapping(value = "/{id}/advertisements")
    public Collection<AdvertisementSimpleDisplayDTO> getAdvertisements(@PathVariable Long id) {
        return advertiserService.getAdvertisements(id);
    }

    @GetMapping(value = "/{id}/reviews/average")
    public AverageRatingDisplayDTO getAverageRating(@PathVariable Long id) {
        return advertiserService.getAverageRating(id);
    }

    @GetMapping(value = "/{id}/reviews")
    public Set<ReviewPublicDisplayDTO> getApprovedReviews(@PathVariable Long id) {
        return advertiserService.getApprovedReviews(id);
    }

    @GetMapping(value = "/reservations")
    @PreAuthorize("hasRole('ADVERTISER')")
    public Page<ReservationSimpleDisplayDTO> getReservations(Authentication auth, @RequestParam int page) {
        return advertiserService.getReservations(auth, PageRequest.of(page, 10, Sort.by("startDateTime")));
    }

    @GetMapping(value = "/reservations/active")
    @PreAuthorize("hasRole('ADVERTISER')")
    public Page<ReservationSimpleDisplayDTO> getActveReservations(Authentication auth, @RequestParam int page) {
        return advertiserService.getActveReservations(auth, PageRequest.of(page, 10, Sort.by("startDateTime")));
    }

    @GetMapping(value = "/reservations/pending-report")
    @PreAuthorize("hasRole('ADVERTISER')")
    public Page<ReservationSimpleDisplayDTO> getUnreportedReservations(Authentication auth, @RequestParam int page) {
        return advertiserService.getUnreportedReservations(auth, PageRequest.of(page, 10, Sort.by("startDateTime")));
    }

    @GetMapping(value = "/advertisements")
    @PreAuthorize("hasRole('ADVERTISER')")
    public Page<AdvertisementSimpleDisplayDTO> getPaginatedAdvertisements(Authentication auth, @RequestParam int page, @RequestParam String sorting) {
        return advertiserService.getPaginatedAdvertisements(auth, PageRequest.of(page, 12, Sort.by("title").descending()));
    }
}
