package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ResortAdCreationDTO;
import com.team4.isamrs.dto.display.ResortAdDisplayDTO;
import org.springframework.data.domain.PageRequest;
import com.team4.isamrs.dto.updation.ResortAdUpdationDTO;
import com.team4.isamrs.model.resort.ResortAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.ResortAdRepository;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ResortAdService {

    @Autowired
    private ResortAdRepository resortAdRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Collection<ResortAdDisplayDTO> findAll() {
        return resortAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, ResortAdDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public ResortAdDisplayDTO findById(Long id) {
        ResortAd resortAd = resortAdRepository.findById(id).orElseThrow();
        return modelMapper.map(resortAd, ResortAdDisplayDTO.class);
    }

    public Collection<ResortAdDisplayDTO> findTopTen() {
        return resortAdRepository.findAll(PageRequest.of(0, 10)).stream()
                .map(e -> modelMapper.map(e, ResortAdDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public ResortAd create(ResortAdCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        ResortAd resortAd = modelMapper.map(dto, ResortAd.class);
        resortAd.setAdvertiser(advertiser);
        resortAd.verifyPhotosOwnership(advertiser);

        resortAdRepository.save(resortAd);
        tagRepository.saveAll(resortAd.getTags());
        return resortAd;
    }

    public void delete(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        ResortAd resortAd = resortAdRepository.findResortAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        resortAd.getTags().forEach(e -> e.getAdvertisements().remove(resortAd));

        resortAdRepository.delete(resortAd);
    }

    public void update(Long id, ResortAdUpdationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        ResortAd resortAd = resortAdRepository.findAdventureAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        modelMapper.map(dto, resortAd);

        resortAd.verifyPhotosOwnership(advertiser);

        tagRepository.saveAll(resortAd.getTags());
        resortAdRepository.save(resortAd);
    }
}
