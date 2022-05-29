package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.CustomerDisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}/reviews")
    public Collection<ServiceReviewDisplayDTO> getReviews(@PathVariable Long id) {
        return customerService.getReviews(id);
    }
}
