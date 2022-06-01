package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.AdvertisementDisplayDTO;
import com.team4.isamrs.dto.display.AdvertisementSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ReservationSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/advertisers",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdvertiserController {

    @Autowired
    private AdvertiserService advertiserService;

    @GetMapping(value = "/{id}/advertisements")
    public Collection<AdvertisementSimpleDisplayDTO> getAdvertisements(@PathVariable Long id) {
        return advertiserService.getAdvertisements(id);
    }

    @GetMapping(value = "/{id}/reviews")
    public Collection<ServiceReviewDisplayDTO> getServiceProviderReview(@PathVariable Long id) {
        return advertiserService.getServiceProviderReview(id);
    }

    @GetMapping(value = "/{id}/rating")
    public Double findRating(@PathVariable Long id) {
        return advertiserService.findRating(id);
    }

    @GetMapping(value="/active-reservations")
    public Page<ReservationSimpleDisplayDTO> findActiveReservations(@RequestParam int page, Authentication auth) {
        return advertiserService.findActiveReservations(PageRequest.of(page, 25), auth);
    }
}
