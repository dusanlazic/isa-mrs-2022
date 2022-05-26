package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.OptionCreationDTO;
import com.team4.isamrs.dto.display.DisplayDTO;
import com.team4.isamrs.dto.display.ServiceReviewDisplayDTO;
import com.team4.isamrs.model.adventure.AdventureAd;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.Option;
import com.team4.isamrs.model.boat.BoatAd;
import com.team4.isamrs.model.review.ServiceReview;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.AdvertisementRepository;
import com.team4.isamrs.repository.OptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private OptionRepository optionRepository;

    public void delete(Long id, Authentication auth) {
        /* Note:
        Do not allow deletion if any future or current reservations for this ad exist.
         */
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findAdvertisementByIdAndAdvertiser(id, advertiser).orElseThrow();

        // Update bidirectional relationships
        advertisement.getTags().forEach(e -> e.getAdvertisements().remove(advertisement));
        if (advertisement instanceof AdventureAd adventureAd) {
            adventureAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(advertisement));
        } else if (advertisement instanceof BoatAd boatAd) {
            boatAd.getNavigationalEquipment().forEach(e -> e.getAdvertisements().remove(advertisement));
            boatAd.getFishingEquipment().forEach(e -> e.getAdvertisements().remove(advertisement));
        }

        advertisementRepository.delete(advertisement);
    }

    public <T extends DisplayDTO> Collection<T> getOptions(Long id, Class<T> returnType) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();

        Collection<Option> options = advertisement.getOptions();
        return options.stream()
                .map(e -> modelMapper.map(e, returnType))
                .collect(Collectors.toSet());
    }

    public void addOption(Long id, OptionCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findAdvertisementByIdAndAdvertiser(id, advertiser).orElseThrow();

        Option option = modelMapper.map(dto, Option.class);
        advertisement.addOption(option);

        advertisementRepository.save(advertisement);
    }

    public void updateOption(Long advertisementId, Long priceId, OptionCreationDTO dto, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findAdvertisementByIdAndAdvertiser(advertisementId, advertiser).orElseThrow();

        Option option = optionRepository.findById(priceId).orElseThrow();
        if (!option.getAdvertisement().equals(advertisement))
            throw new NoSuchElementException();

        modelMapper.map(dto, option);

        optionRepository.save(option);
    }

    public void removeOption(Long advertisementId, Long optionId, Authentication auth) {
        Advertiser advertiser = (Advertiser) auth.getPrincipal();
        Advertisement advertisement = advertisementRepository.findAdvertisementByIdAndAdvertiser(advertisementId, advertiser).orElseThrow();
        Option option = optionRepository.findById(optionId).orElseThrow();
        if (!option.getAdvertisement().equals(advertisement))
            throw new NoSuchElementException();

        advertisement.removeOption(option);

        advertisementRepository.save(advertisement);
        optionRepository.delete(option); // might not be needed because of auto orphan removal
    }

    public double findRating(Long id) {
        Set<ServiceReview> serviceReviews = advertisementRepository.findById(id).orElseThrow().getReviews();
        double rating = 0;
        if (serviceReviews.size() > 0)
            rating = serviceReviews.stream().mapToDouble(ServiceReview::getRating).sum() / serviceReviews.size();
        return Math.round(rating * 100.0) / 100.0;
    }

    public Collection<ServiceReviewDisplayDTO> getReviews(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id).orElseThrow();
        return advertisement.getReviews().stream()
                .map(e -> modelMapper.map(e, ServiceReviewDisplayDTO.class))
                .collect(Collectors.toSet());
    }
}
