package com.team4.isamrs.controller;

import com.team4.isamrs.dto.ResponseCreated;
import com.team4.isamrs.dto.creation.TagCreationDTO;
import com.team4.isamrs.dto.display.TagDisplayDTO;
import com.team4.isamrs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/tags",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public Collection<TagDisplayDTO> findAll() {
        return tagService.findAll(TagDisplayDTO.class);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public TagDisplayDTO findById(@PathVariable Long id) {
        return tagService.findById(id, TagDisplayDTO.class);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseCreated create(@Valid @RequestBody TagCreationDTO dto) {
        return new ResponseCreated("Tag created.", tagService.create(dto).getId());
    }
}
