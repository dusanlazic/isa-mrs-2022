package com.team4.isamrs.service;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.Option;
import com.team4.isamrs.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Optional<Advertisement> findById(Long id) {
        return advertisementRepository.findById(id);
    }

    public boolean addOptionToAdvertisement(Option option, Advertisement advertisement) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        advertisement.addOption(option);
        advertisementRepository.save(advertisement);
        return true;
    }
}
