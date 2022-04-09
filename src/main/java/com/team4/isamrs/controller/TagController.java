package com.team4.isamrs.controller;

import com.team4.isamrs.dto.TagCreationDTO;
import com.team4.isamrs.dto.TagDisplayDTO;
import com.team4.isamrs.model.entity.advertisement.Tag;
import com.team4.isamrs.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*")
public class TagController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TagService tagService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TagDisplayDTO>> findAllTags() {
        Collection<Tag> tags = tagService.findAll();

        Collection<TagDisplayDTO> dto = tags.stream()
                .map(e -> modelMapper.map(e, TagDisplayDTO.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDisplayDTO> findTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.findById(id);

        if (tag.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        TagDisplayDTO dto = modelMapper.map(tag.get(), TagDisplayDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.FOUND);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addTag(@Valid @RequestBody TagCreationDTO dto) throws URISyntaxException {
        Tag tag = modelMapper.map(dto, Tag.class);

        Long id = tagService.createTag(tag);
        if (tag == null)
            return ResponseEntity.internalServerError().build();

        String uri = "/tags/" + id;
        return ResponseEntity.created(new URI(uri)).body("New Tag created at: " + uri);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
