package com.team4.isamrs.controller;

import com.team4.isamrs.model.advertisement.Photo;
import com.team4.isamrs.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/photos")
@CrossOrigin(origins = "*")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findPhotoById(@PathVariable UUID id) {
        Optional<Photo> photo = photoService.findById(id);

        if (photo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addPhoto() throws URISyntaxException {
        UUID id = photoService.createPhoto();
        if (id == null)
            return ResponseEntity.internalServerError().build();

        String uri = "/photos/" + id;
        return ResponseEntity.created(new URI(uri)).body("New Photo created at: " + uri);
    }
}
