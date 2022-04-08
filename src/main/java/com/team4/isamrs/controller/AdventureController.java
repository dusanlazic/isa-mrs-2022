package com.team4.isamrs.controller;

import com.team4.isamrs.dto.AdventureAdFormDto;
import com.team4.isamrs.model.entity.adventure.AdventureAd;
import com.team4.isamrs.service.AdventureAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
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
    public ResponseEntity<?> addAdventureAd(@Valid @RequestBody AdventureAdFormDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        // save user

        return ResponseEntity.ok("Validation checks passed.");
    }
}
