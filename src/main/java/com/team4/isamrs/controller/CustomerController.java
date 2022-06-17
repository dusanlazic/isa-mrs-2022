package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.CustomerDisplayDTO;
import com.team4.isamrs.dto.display.ReservationSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ReviewPublicDisplayDTO;
import com.team4.isamrs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/customers",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
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

    @GetMapping(value = "/{id}/previous-reservations")
    public Page<ReservationSimpleDisplayDTO> getPreviousReservations(
            @PathVariable Long id, @RequestParam int page, @RequestParam String sorting) {
        return customerService.getPreviousReservations(id, PageRequest.of(page, 10, Sort.by(sorting).descending()));
    }

    @GetMapping(value = "/{id}/upcoming-reservations")
    public Page<ReservationSimpleDisplayDTO> getActiveReservations(
            @PathVariable Long id, @RequestParam int page, @RequestParam String sorting) {
        return customerService.getUpcomingReservations(id, PageRequest.of(page, 10, Sort.by(sorting).descending()));
    }
}
