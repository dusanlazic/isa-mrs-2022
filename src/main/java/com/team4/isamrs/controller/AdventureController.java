package com.team4.isamrs.controller;

import com.team4.isamrs.model.entity.adventure.AdventureAd;
import com.team4.isamrs.service.AdventureAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/ads/adventures")
@CrossOrigin(origins = "*")
public class AdventureController {

    @Autowired
    private AdventureAdService adventureAdService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdventureAd>> findAllAdventureAds() {
        Collection<AdventureAd> adventureAds = adventureAdService.findAll();
        return new ResponseEntity<Collection<AdventureAd>>(adventureAds, HttpStatus.OK);
    }
}
