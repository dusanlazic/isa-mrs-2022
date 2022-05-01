package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.PhotoBriefDisplayDTO;
import com.team4.isamrs.dto.display.PhotoUploadDisplayDTO;
import com.team4.isamrs.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/photos")
@CrossOrigin(origins = "*")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("metadata/{id}")
    public ResponseEntity<PhotoBriefDisplayDTO> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(photoService.findById(id, PhotoBriefDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/{filename}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> serve(@PathVariable String filename) throws IOException {
        return photoService.serve(filename);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, Authentication authentication) {
        PhotoUploadDisplayDTO response = photoService.store(file, authentication);
        return ResponseEntity.created(URI.create(response.getUri()))
                             .body(response);
    }
}
