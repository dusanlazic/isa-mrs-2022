package com.team4.isamrs.controller;

import com.team4.isamrs.dto.display.AccountDisplayDTO;
import com.team4.isamrs.dto.display.AdvertisementSimpleDisplayDTO;
import com.team4.isamrs.dto.display.ReviewAdminDisplayDTO;
import com.team4.isamrs.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/advertisers",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AdvertiserController {

    @Autowired
    private AdvertiserService advertiserService;

    @GetMapping(value = "/{id}")
    public AccountDisplayDTO getAdvertiser(@PathVariable Long id) {
        return advertiserService.findById(id);
    }

    @GetMapping(value = "/{id}/advertisements")
    public Collection<AdvertisementSimpleDisplayDTO> getAdvertisements(@PathVariable Long id) {
        return advertiserService.getAdvertisements(id);
    }

    @GetMapping(value = "/{id}/rating")
    public Double findRating(@PathVariable Long id) {
        return advertiserService.findRating(id);
    }
}
