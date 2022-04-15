package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.BoatAdDisplayDTO;
import com.team4.isamrs.service.BoatAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/ads/boats",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BoatController {

    @Autowired
    private BoatAdService boatAdService;

    @GetMapping(value = "")
    public ResponseEntity<Collection<BoatAdDisplayDTO>> findAll() {
        return new ResponseEntity<>(boatAdService.findAll(BoatAdDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoatAdDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(boatAdService.findById(id, BoatAdDisplayDTO.class), HttpStatus.OK);
    }
}
