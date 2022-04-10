package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.OptionCreationDTO;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.Option;
import com.team4.isamrs.repository.AdvertisementRepository;
import com.team4.isamrs.repository.OptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdvertisementService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private OptionRepository optionRepository;

    public Optional<Advertisement> findById(Long id) {
        return advertisementRepository.findById(id);
    }

    public boolean addOptionToAdvertisement(Option option, Advertisement advertisement) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        advertisement.addOption(option);

        try {
            advertisementRepository.save(advertisement);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeOption(Option option, Advertisement advertisement) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        if (!option.getAdvertisement().equals(advertisement))
            return false;

        advertisement.removeOption(option);

        try {
            advertisementRepository.save(advertisement);
            optionRepository.delete(option);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateOption(Option option, Advertisement advertisement, OptionCreationDTO dto) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        if (!option.getAdvertisement().equals(advertisement))
            return false;

        modelMapper.map(dto, option);
        try {
            optionRepository.save(option);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
