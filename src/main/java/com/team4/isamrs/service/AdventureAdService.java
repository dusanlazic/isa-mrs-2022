package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.display.AdventureAdSimpleDisplayDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.model.advertisement.AdventureAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdventureAdService {

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAll(Class<T> returnType) {
        return adventureAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }


    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        AdventureAd adventureAd = adventureAdRepository.findById(id).orElseThrow();

        return modelMapper.map(adventureAd, returnType);
    }

    public Collection<AdventureAdSimpleDisplayDTO> findTopSix() {
        return adventureAdRepository.findAll(PageRequest.of(0, 6)).stream()
                .map(e -> modelMapper.map(e, AdventureAdSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
    }
    public Page<AdventureAdSimpleDisplayDTO> search(int page) {
        return adventureAdRepository.findAll(PageRequest.of(page, 20))
                .map(e -> modelMapper.map(e, AdventureAdSimpleDisplayDTO.class));
    }

    public AdventureAd create(AdventureAdCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();

        AdventureAd adventureAd = modelMapper.map(dto, AdventureAd.class);

        adventureAd.setAdvertiser(advertiser);
        adventureAd.verifyPhotosOwnership(advertiser);

        adventureAdRepository.save(adventureAd);
        tagRepository.saveAll(adventureAd.getTags());
        fishingEquipmentRepository.saveAll(adventureAd.getFishingEquipment());
        return adventureAd;
    }

    public void update(Long id, AdventureAdUpdationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        AdventureAd adventureAd = adventureAdRepository.findAdventureAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        modelMapper.map(dto, adventureAd);

        adventureAd.verifyPhotosOwnership(advertiser);

        fishingEquipmentRepository.saveAll(adventureAd.getFishingEquipment());
        tagRepository.saveAll(adventureAd.getTags());
        adventureAdRepository.save(adventureAd);
    }

    public void delete(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        AdventureAd adventureAd = adventureAdRepository.findAdventureAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        adventureAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(adventureAd)); // there must be a better solution
        adventureAd.getTags().forEach(e -> e.getAdvertisements().remove(adventureAd));

        adventureAdRepository.delete(adventureAd);
    }
}
