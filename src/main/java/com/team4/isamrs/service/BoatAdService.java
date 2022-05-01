package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.model.boat.BoatAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.BoatAdRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BoatAdService {

    @Autowired
    private BoatAdRepository boatAdRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAll(Class<T> returnType) {
        return boatAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        BoatAd boatAd = boatAdRepository.findById(id).orElseThrow();
        return modelMapper.map(boatAd, returnType);
    }

    public BoatAd create(BoatAdCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();

        BoatAd boatAd = modelMapper.map(dto, BoatAd.class);
        boatAd.setAdvertiser(advertiser);
        boatAd.verifyPhotosOwnership(advertiser);

        boatAdRepository.save(boatAd);
        return boatAd;
    }
}
