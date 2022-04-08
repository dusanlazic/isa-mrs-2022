package com.team4.isamrs.controller;

import com.team4.isamrs.model.entity.adventure.AdventureAd;
import com.team4.isamrs.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ads")
@CrossOrigin(origins = "*")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/adventures")
    public List<AdventureAd> findAll() {
        return advertisementService.findAll();
    }
}
