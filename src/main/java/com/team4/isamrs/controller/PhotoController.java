package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.PhotoDisplayDTO;
import com.team4.isamrs.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/photos")
@CrossOrigin(origins = "*")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("metadata/{id}")
    public ResponseEntity<PhotoDisplayDTO> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(photoService.findById(id, PhotoDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> serve(@PathVariable UUID id) throws MalformedURLException {
        return photoService.serve(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.created(URI.create("/photos/" + photoService.store(file).getId()))
                             .body("Photo uploaded.");
    }
}
