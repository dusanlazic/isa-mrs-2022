package com.team4.isamrs.service;

import com.team4.isamrs.dto.AdventureAdCreationDTO;
import com.team4.isamrs.model.entity.adventure.AdventureAd;
import com.team4.isamrs.repository.AdventureAdRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AdventureAdService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    public Collection<AdventureAd> findAll() {
        return adventureAdRepository.findAll();
    }

    public Optional<AdventureAd> findById(Long id) {
        return adventureAdRepository.findById(id);
    }

    public AdventureAd createAdventureAd(AdventureAdCreationDTO dto) {
        AdventureAd adventureAd = modelMapper.map(dto, AdventureAd.class);

        try {
            adventureAdRepository.save(adventureAd);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return adventureAd;
    }
}
