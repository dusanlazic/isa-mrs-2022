package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.AdvertisementDisplayDTO;
import com.team4.isamrs.dto.display.CustomerDisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Collection<AdvertisementDisplayDTO>> getAdvertisements(@PathVariable Long id) {
        return new ResponseEntity<>(advertiserService.getAdvertisements(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/reviews")
    public ResponseEntity<Collection<ServiceReviewDisplayDTO>> getServiceProviderReview(@PathVariable Long id) {
        return new ResponseEntity<>(advertiserService.getServiceProviderReview(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/rating")
    public ResponseEntity<Double> findRating(@PathVariable Long id) {
        return new ResponseEntity<>(advertiserService.findRating(id), HttpStatus.OK);
    }
}
