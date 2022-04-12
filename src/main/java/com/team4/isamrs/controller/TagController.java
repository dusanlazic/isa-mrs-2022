package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.TagCreationDTO;
import com.team4.isamrs.dto.display.TagDisplayDTO;
import com.team4.isamrs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/tags",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Collection<TagDisplayDTO>> findAll() {
        return new ResponseEntity<>(tagService.findAll(TagDisplayDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<TagDisplayDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.findById(id, TagDisplayDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody TagCreationDTO dto) {
        return ResponseEntity.created(URI.create("/tags/" + tagService.create(dto).getId()))
                             .body("Tag created.");
    }
}
