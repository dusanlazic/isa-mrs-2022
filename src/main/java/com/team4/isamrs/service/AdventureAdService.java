package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.creation.HourlyPriceCreationDTO;
import com.team4.isamrs.dto.display.AdventureAdDisplayDTO;
import com.team4.isamrs.dto.display.AdventureAdSimpleDisplayDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.model.adventure.AdventureAd;
import com.team4.isamrs.model.adventure.FishingEquipment;
import com.team4.isamrs.model.advertisement.HourlyPrice;
import com.team4.isamrs.model.advertisement.Tag;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.AdventureAdRepository;
import com.team4.isamrs.repository.FishingEquipmentRepository;
import com.team4.isamrs.repository.HourlyPriceRepository;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdventureAdService {

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    @Autowired
    private HourlyPriceRepository hourlyPriceRepository;

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

    public Collection<AdventureAdSimpleDisplayDTO> findTopSix() {
        return adventureAdRepository.findAll(PageRequest.of(0, 6)).stream()
                .map(e -> modelMapper.map(e, AdventureAdSimpleDisplayDTO.class))
                .collect(Collectors.toSet());
    }

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        AdventureAd adventureAd = adventureAdRepository.findById(id).orElseThrow();

        return modelMapper.map(adventureAd, returnType);
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

    public <T extends DisplayDTO> Collection<T> getPrices(Long id, Class<T> returnType) {
        AdventureAd adventureAd = adventureAdRepository.findById(id).orElseThrow();

        Collection<HourlyPrice> prices = adventureAd.getPrices();
        return prices.stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public HourlyPrice addPrice(Long id, HourlyPriceCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        AdventureAd adventureAd = adventureAdRepository.findAdventureAdByIdAndAdvertiser(id, advertiser).orElseThrow();

        HourlyPrice hourlyPrice = modelMapper.map(dto, HourlyPrice.class);
        adventureAd.addHourlyPrice(hourlyPrice);

        adventureAdRepository.save(adventureAd);
        return hourlyPrice;
    }

    public void updatePrice(Long advertisementId, Long priceId, HourlyPriceCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        AdventureAd adventureAd = adventureAdRepository.findAdventureAdByIdAndAdvertiser(advertisementId, advertiser).orElseThrow();
        HourlyPrice hourlyPrice = hourlyPriceRepository.findById(priceId).orElseThrow();
        if (!adventureAd.getPrices().contains(hourlyPrice))
            throw new NoSuchElementException();

        modelMapper.map(dto, hourlyPrice);

        hourlyPriceRepository.save(hourlyPrice);
    }

    public void removePrice(Long advertisementId, Long priceId, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        AdventureAd adventureAd = adventureAdRepository.findAdventureAdByIdAndAdvertiser(advertisementId, advertiser).orElseThrow();
        HourlyPrice hourlyPrice = hourlyPriceRepository.findById(priceId).orElseThrow();
        if (!adventureAd.getPrices().contains(hourlyPrice))
            throw new NoSuchElementException();

        adventureAd.removeHourlyPrice(hourlyPrice);

        adventureAdRepository.save(adventureAd);
        hourlyPriceRepository.delete(hourlyPrice); // might not be needed because of auto orphan removal
    }
}
