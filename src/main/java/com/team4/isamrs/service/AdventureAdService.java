package com.team4.isamrs.service;

import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.model.adventure.AdventureAd;
import com.team4.isamrs.repository.AdventureAdRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AdventureAdService {

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Collection<AdventureAd> findAll() {
        return adventureAdRepository.findAll();
    }

    public Optional<AdventureAd> findById(Long id) {
        return adventureAdRepository.findById(id);
    }

    public Long createAdventureAd(AdventureAd adventureAd) {
        /* Note:
        Every photo in adventureAd.photos should be checked if
        it's uploaded by the current logged-in user.
         */
        try {
            adventureAdRepository.save(adventureAd);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return adventureAd.getId();
    }

    public Boolean updateAdventureAd(AdventureAd adventureAd, AdventureAdUpdationDTO dto) {
        modelMapper.map(dto, adventureAd);
        try {
            adventureAdRepository.save(adventureAd);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
