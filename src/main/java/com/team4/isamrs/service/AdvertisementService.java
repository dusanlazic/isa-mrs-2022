package com.team4.isamrs.service;

import com.team4.isamrs.model.entity.adventure.AdventureAd;
import com.team4.isamrs.repository.AdventureAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    public List<AdventureAd> findAll() {
        return adventureAdRepository.findAll();
    }
}
