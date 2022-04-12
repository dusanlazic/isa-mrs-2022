package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.creation.HourlyPriceCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.model.adventure.AdventureAd;
import com.team4.isamrs.model.advertisement.HourlyPrice;
import com.team4.isamrs.repository.AdventureAdRepository;
import com.team4.isamrs.repository.HourlyPriceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdventureAdService {

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    @Autowired
    private HourlyPriceRepository hourlyPriceRepository;

    @Autowired
    private ModelMapper modelMapper;

    public <T extends DisplayDTO> Collection<T> findAll(Class<T> returnType) {
        return adventureAdRepository.findAll().stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public <T extends DisplayDTO> T findById(Long id, Class<T> returnType) {
        // Todo: Custom exception handled by controller advisor
        AdventureAd adventureAd = adventureAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adventure ad not found."));

        return modelMapper.map(adventureAd, returnType);
    }

    public AdventureAd create(AdventureAdCreationDTO dto) {
        /* Note:
        Every photo in adventureAd.photos should be checked if
        it's uploaded by the current logged-in user.
         */
        AdventureAd adventureAd = modelMapper.map(dto, AdventureAd.class);

        adventureAdRepository.save(adventureAd);
        return adventureAd;
    }

    public void update(Long id, AdventureAdUpdationDTO dto) {
        /* Note:
        Every photo in adventureAd.photos should be checked if
        it's uploaded by the current logged-in user.
         */
        AdventureAd adventureAd = adventureAdRepository.findById(id).orElseThrow(NullPointerException::new);

        modelMapper.map(dto, adventureAd);

        adventureAdRepository.save(adventureAd);
    }

    public void delete(Long id) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        AdventureAd adventureAd = adventureAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adventure ad has reservations."));

        adventureAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(adventureAd)); // there must be a better solution

        adventureAdRepository.delete(adventureAd);
    }

    public <T extends DisplayDTO> Collection<T> getPrices(Long id, Class<T> returnType) {
        AdventureAd adventureAd = adventureAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adventure ad not found."));

        Collection<HourlyPrice> prices = adventureAd.getPrices();
        return prices.stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public HourlyPrice addPrice(Long id, HourlyPriceCreationDTO dto) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        AdventureAd adventureAd = adventureAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adventure ad not found."));

        HourlyPrice hourlyPrice = modelMapper.map(dto, HourlyPrice.class);
        adventureAd.addHourlyPrice(hourlyPrice);

        adventureAdRepository.save(adventureAd);
        return hourlyPrice;
    }

    public void updatePrice(Long advertisementId, Long priceId, HourlyPriceCreationDTO dto) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        AdventureAd adventureAd = adventureAdRepository.findById(advertisementId).orElseThrow();
        HourlyPrice hourlyPrice = hourlyPriceRepository.findById(priceId).orElseThrow();
        if (!adventureAd.getPrices().contains(hourlyPrice))
            throw new RuntimeException("Price does not belong to Adventure ad.");

        modelMapper.map(dto, hourlyPrice);

        hourlyPriceRepository.save(hourlyPrice);
    }

    public void removePrice(Long advertisementId, Long priceId) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        AdventureAd adventureAd = adventureAdRepository.findById(advertisementId)
                .orElseThrow(() -> new RuntimeException("Adventure ad not found."));
        HourlyPrice hourlyPrice = hourlyPriceRepository.findById(priceId)
                .orElseThrow(() -> new RuntimeException("Price not found."));
        if (!adventureAd.getPrices().contains(hourlyPrice))
            throw new RuntimeException("Price does not belong to Adventure ad.");

        adventureAd.removeHourlyPrice(hourlyPrice);

        adventureAdRepository.save(adventureAd);
        hourlyPriceRepository.delete(hourlyPrice); // might not be needed because of auto orphan removal
    }
}
