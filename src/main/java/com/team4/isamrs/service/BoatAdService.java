package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.display.BoatAdDisplayDTO;
import com.team4.isamrs.dto.display.BoatAdSimpleDisplayDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.display.ResortAdSimpleDisplayDTO;
import com.team4.isamrs.dto.updation.BoatAdUpdationDTO;
import com.team4.isamrs.model.boat.BoatAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.BoatAdRepository;
import com.team4.isamrs.repository.FishingEquipmentRepository;
import com.team4.isamrs.repository.NavigationalEquipmentRepository;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BoatAdService {

    @Autowired
    private BoatAdRepository boatAdRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    @Autowired
    private NavigationalEquipmentRepository navigationalEquipmentRepository;

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

    public Collection<BoatAdSimpleDisplayDTO> findTopSix() {
        return boatAdRepository.findAll(PageRequest.of(0, 6)).stream()
                .map(e -> modelMapper.map(e, BoatAdSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public Page<BoatAdSimpleDisplayDTO> search(int page) {
        return boatAdRepository.findAll(PageRequest.of(page, 20))
                .map(e -> modelMapper.map(e, BoatAdSimpleDisplayDTO.class));
    }

    public BoatAd create(BoatAdCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        BoatAd boatAd = modelMapper.map(dto, BoatAd.class);
        boatAd.setAdvertiser(advertiser);
        boatAd.verifyPhotosOwnership(advertiser);

        boatAdRepository.save(boatAd);
        tagRepository.saveAll(boatAd.getTags());
        fishingEquipmentRepository.saveAll(boatAd.getFishingEquipment());
        navigationalEquipmentRepository.saveAll(boatAd.getNavigationalEquipment());
        return boatAd;
    }

    public void delete(Long id, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        BoatAd boatAd = boatAdRepository.findBoatAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        boatAd.getNavigationalEquipment().forEach(e -> e.getAdvertisements().remove(boatAd));
        boatAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(boatAd));
        boatAd.getTags().forEach(e -> e.getAdvertisements().remove(boatAd));

        boatAdRepository.delete(boatAd);
    }

    public void update(Long id, BoatAdUpdationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        BoatAd boatAd = boatAdRepository.findBoatAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        modelMapper.map(dto, boatAd);

        boatAd.verifyPhotosOwnership(advertiser);

        fishingEquipmentRepository.saveAll(boatAd.getFishingEquipment());
        tagRepository.saveAll(boatAd.getTags());
        boatAdRepository.save(boatAd);
    }
}
