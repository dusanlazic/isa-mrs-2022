package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.OptionCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.Option;
import com.team4.isamrs.repository.AdvertisementRepository;
import com.team4.isamrs.repository.OptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private OptionRepository optionRepository;

    public <T extends DisplayDTO> Collection<T> getOptions(Long id, Class<T> returnType) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();

        Collection<Option> options = advertisement.getOptions();
        return options.stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public void addOption(Long id, OptionCreationDTO dto) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();

        Option option = modelMapper.map(dto, Option.class);
        advertisement.addOption(option);

        advertisementRepository.save(advertisement);
    }

    public void updateOption(Long advertisementId, Long priceId, OptionCreationDTO dto) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow();
        Option option = optionRepository.findById(priceId).orElseThrow();
        if (!option.getAdvertisement().equals(advertisement))
            throw new RuntimeException("Price does not belong to Adventure ad.");

        modelMapper.map(dto, option);

        optionRepository.save(option);
    }

    public void removeOption(Long advertisementId, Long optionId) {
        /* Note:
        Ensure that this advertisement is posted by the current logged-in user.
         */
        Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow();
        Option option = optionRepository.findById(optionId).orElseThrow();
        if (!option.getAdvertisement().equals(advertisement))
            throw new RuntimeException("Price does not belong to Adventure ad.");

        advertisement.removeOption(option);

        advertisementRepository.save(advertisement);
        optionRepository.delete(option); // might not be needed because of auto orphan removal
    }
}
