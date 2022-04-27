package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.ResortAdDisplayDTO;
import com.team4.isamrs.service.ResortAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/ads/resorts",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ResortController {

    @Autowired
    private ResortAdService resortAdService;

    @GetMapping(value = "")
    public ResponseEntity<Collection<ResortAdDisplayDTO>> findAll() {
        return new ResponseEntity<>(resortAdService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResortAdDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(resortAdService.findById(id), HttpStatus.OK);
    }
}