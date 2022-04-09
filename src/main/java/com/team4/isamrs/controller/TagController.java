package com.team4.isamrs.controller;

import com.team4.isamrs.dto.TagCreationDTO;
import com.team4.isamrs.model.entity.advertisement.Tag;
import com.team4.isamrs.service.TagService;
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

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Tag>> findAllTags() {
        Collection<Tag> tags = tagService.findAll();
        return new ResponseEntity<Collection<Tag>>(tags, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> findTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.findById(id);

        if (tag.isEmpty())
            return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Tag>(tag.get(), HttpStatus.FOUND);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addTag(@Valid @RequestBody TagCreationDTO dto) throws URISyntaxException {
        Tag tag = tagService.createTag(dto);

        if (tag == null)
            return ResponseEntity.internalServerError().build();

        String uri = "/tags/" + tag.getId();
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
