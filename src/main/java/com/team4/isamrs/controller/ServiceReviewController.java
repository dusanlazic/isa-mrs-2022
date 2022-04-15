package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.service.ServiceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/ads/service-reviews",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ServiceReviewController {
    @Autowired
    private ServiceReviewService serviceReviewService;

    @GetMapping(value = "")
    public ResponseEntity<Collection<ServiceReviewDisplayDTO>> findAll() {
        return new ResponseEntity<>(serviceReviewService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceReviewDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(serviceReviewService.findById(id), HttpStatus.OK);
    }
}
