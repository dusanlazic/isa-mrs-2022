package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.PhotoDisplayDTO;
import com.team4.isamrs.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping(
        value = "/photos",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/{id}")
    public ResponseEntity<PhotoDisplayDTO> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(photoService.findById(id, PhotoDisplayDTO.class), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create() throws URISyntaxException {
        return ResponseEntity.created(URI.create("/photos/" + photoService.create().getId()))
                             .body("Photo created.");
    }
}
