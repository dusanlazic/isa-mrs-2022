package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.HourlyPriceCreationDTO;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.model.adventure.AdventureAd;
import com.team4.isamrs.model.advertisement.HourlyPrice;
import com.team4.isamrs.repository.AdventureAdRepository;
import com.team4.isamrs.repository.HourlyPriceRepository;
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
    private HourlyPriceRepository hourlyPriceRepository;

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
        /* Note:
        Every photo in adventureAd.photos should be checked if
        it's uploaded by the current logged-in user.
         */
        modelMapper.map(dto, adventureAd);
        try {
            adventureAdRepository.save(adventureAd);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addPriceToAdventureAd(HourlyPrice hourlyPrice, AdventureAd adventureAd) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        adventureAd.addHourlyPrice(hourlyPrice);

        try {
            adventureAdRepository.save(adventureAd);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removePrice(HourlyPrice hourlyPrice, AdventureAd adventureAd) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        if (!adventureAd.getPrices().contains(hourlyPrice))
            return false;

        adventureAd.removeHourlyPrice(hourlyPrice);

        try {
            adventureAdRepository.save(adventureAd);
            hourlyPriceRepository.delete(hourlyPrice);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updatePrice(HourlyPrice hourlyPrice, AdventureAd adventureAd, HourlyPriceCreationDTO dto) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        if (!adventureAd.getPrices().contains(hourlyPrice))
            return false;

        modelMapper.map(dto, hourlyPrice);
        try {
            hourlyPriceRepository.save(hourlyPrice);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
