package com.team4.isamrs.controller;

import com.team4.isamrs.dto.AdventureAdCreationDTO;
import com.team4.isamrs.model.entity.adventure.AdventureAd;
import com.team4.isamrs.service.AdventureAdService;
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
@RequestMapping("/ads/adventures")
@CrossOrigin(origins = "*")
public class AdventureController {

    @Autowired
    private AdventureAdService adventureAdService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdventureAd>> findAllAdventureAds() {
        Collection<AdventureAd> adventureAds = adventureAdService.findAll();
        return new ResponseEntity<Collection<AdventureAd>>(adventureAds, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdventureAd> findAdventureAdById(@PathVariable Long id) {
        Optional<AdventureAd> adventureAd = adventureAdService.findById(id);

        if (adventureAd.isEmpty())
            return new ResponseEntity<AdventureAd>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<AdventureAd>(adventureAd.get(), HttpStatus.FOUND);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addAdventureAd(@Valid @RequestBody AdventureAdCreationDTO dto) throws URISyntaxException {
        AdventureAd adventureAd = adventureAdService.createAdventureAd(dto);

        if (adventureAd == null)
            return ResponseEntity.internalServerError().build();

        String uri = "/ads/adventures/" + adventureAd.getId();
        return ResponseEntity.created(new URI(uri)).body("New adventure ad created at: " + uri);
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
