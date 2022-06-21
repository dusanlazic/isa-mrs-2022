package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.AdvertisementSimpleDisplayDTO;
import com.team4.isamrs.dto.display.CustomerDisplayDTO;
import com.team4.isamrs.dto.display.ReservationSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ReviewPublicDisplayDTO;
import com.team4.isamrs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/customers",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/{id}")
    public CustomerDisplayDTO findById(@PathVariable Long id) {
        return customerService.findById(id, CustomerDisplayDTO.class);
    }

    @GetMapping(value = "/{id}/reviews")
    public Collection<ReviewPublicDisplayDTO> getReviews(@PathVariable Long id) {
        return customerService.getReviews(id);
    }

    @GetMapping(value = "/previous-reservations")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Page<ReservationSimpleDisplayDTO> getPreviousReservations(@RequestParam int page,
                                                                     @RequestParam String sorting,
                                                                     Authentication auth) {
        return customerService.getPreviousReservations(PageRequest.of(page, 10, Sort.by(sorting)), auth);
    }

    @GetMapping(value = "/upcoming-reservations")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Page<ReservationSimpleDisplayDTO> getActiveReservations(@RequestParam int page,
                                                                   @RequestParam String sorting,
                                                                   Authentication auth) {
        return customerService.getUpcomingReservations(PageRequest.of(page, 10, Sort.by(sorting)), auth);
    }

    @GetMapping(value = "/subscriptions")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Collection<AdvertisementSimpleDisplayDTO> getSubscriptions(Authentication auth) {
        return customerService.getSubscriptions(auth);
    }
}
