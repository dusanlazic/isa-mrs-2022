package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.BoatAdDisplayDTO;
import com.team4.isamrs.dto.display.CustomerDisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CustomerDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id, CustomerDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<Collection<ServiceReviewDisplayDTO>> getReviews(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getReviews(id), HttpStatus.OK);
    }
}
