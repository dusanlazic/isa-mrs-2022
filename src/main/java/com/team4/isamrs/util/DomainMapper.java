package com.team4.isamrs.util;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;
import com.team4.isamrs.dto.creation.ResortAdCreationDTO;
import com.team4.isamrs.dto.display.*;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.dto.updation.HourlyPriceUpdationDTO;
import com.team4.isamrs.dto.updation.OptionUpdationDTO;
import com.team4.isamrs.model.adventure.AdventureAd;
import com.team4.isamrs.model.advertisement.HourlyPrice;
import com.team4.isamrs.model.advertisement.Option;
import com.team4.isamrs.model.advertisement.Photo;
import com.team4.isamrs.model.advertisement.Tag;
import com.team4.isamrs.model.boat.BoatAd;
import com.team4.isamrs.model.enumeration.AccountType;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.resort.ResortAd;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.model.user.RegistrationRequest;
import com.team4.isamrs.repository.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainMapper {

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private FishingEquipmentRepository fishingEquipmentRepository;

    @Autowired
    private NavigationalEquipmentRepository navigationalEquipmentRepository;

    @Bean
    public ModelMapper ModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        configure(modelMapper);
        addConverters(modelMapper);

        return modelMapper;
    }

    private void configure(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    private void addConverters(ModelMapper modelMapper) {
        Converter<AdventureAdCreationDTO, AdventureAd> CreationDtoToAdventureAdConverter = context -> {
            AdventureAdCreationDTO source = context.getSource();
            AdventureAd destination = context.getDestination();

            // Lookup IDs and fill the collections
            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));

            // Sync bidirectional relationships
            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        Converter<AdventureAdUpdationDTO, AdventureAd> UpdationDtoToAdventureAdConverter = context -> {
            AdventureAdUpdationDTO source = context.getSource();
            AdventureAd destination = context.getDestination();

            // Lookup IDs and fill the collections
            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));

            // Delete options marked for deletion
            int index = 0;
            List<Option> removedOptions = new LinkedList<>();
            for (OptionUpdationDTO dto: source.getOptions()) {
                if (dto.getDelete() != null) {
                    Option option = destination.getOptions().get(index);
                    if (option != null)
                        removedOptions.add(option);
                }
                index++;
            }
            removedOptions.forEach(destination::removeOption);

            // Delete prices marked for deletion
            index = 0;
            List<HourlyPrice> removedPrices = new LinkedList<>();
            for (HourlyPriceUpdationDTO dto: source.getPrices()) {
                if (dto.getDelete() != null) {
                    HourlyPrice price = destination.getPrices().get(index);
                    if (price != null)
                        removedPrices.add(price);
                }
                index++;
            }
            removedPrices.forEach(destination::removeHourlyPrice);

            // Sync bidirectional relationships
            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        Converter<AdventureAd, AdventureAdDisplayDTO> AdventureAdToDisplayDtoConverter = context -> {
            AdventureAd source = context.getSource();
            AdventureAdDisplayDTO destination = context.getDestination();

            destination.setTags(source.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

            return destination;
        };

        Converter<Photo, PhotoBriefDisplayDTO> PhotoToDisplayDtoConverter = context -> {
            context.getDestination().setUri("/photos/" + context.getSource().getStoredFilename());
            return context.getDestination();
        };

        Converter<Photo, PhotoUploadDisplayDTO> PhotoToUploadDisplayDtoConverter = context -> {
            context.getDestination().setUri("/photos/" + context.getSource().getStoredFilename());
            return context.getDestination();
        };

        Converter<BoatAdCreationDTO, BoatAd> CreationDtoToBoatAdConverter = context -> {
            BoatAdCreationDTO source = context.getSource();
            BoatAd destination = context.getDestination();

            // Lookup IDs and fill the collections
            source.getTagIds().forEach(id -> destination.addTag(tagRepository.findById(id).get()));
            source.getFishingEquipmentIds().forEach(id -> destination.addFishingEquipment(fishingEquipmentRepository.findById(id).get()));
            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));
            source.getNavigationalEquipmentIds().forEach(id -> destination.addNavigationalEquipment(navigationalEquipmentRepository.findById(id).get()));

            // Sync bidirectional relationships
            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        Converter<BoatAd, BoatAdDisplayDTO> BoatAdToDisplayDtoConverter = context -> {
            BoatAd source = context.getSource();
            BoatAdDisplayDTO destination = context.getDestination();

            destination.setTags(source.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

            return destination;
        };

        Converter<RegistrationRequestCreationDTO, RegistrationRequest> RegistrationRequestCreationDTOToRegistrationRequest = context -> {
            RegistrationRequestCreationDTO source = context.getSource();
            RegistrationRequest destination = context.getDestination();

            destination.setAccountType(AccountType.values()[source.getAccountType()]);
            destination.setApprovalStatus(ApprovalStatus.PENDING);

            return destination;
        };

        Converter<ResortAdCreationDTO, ResortAd> CreationDtoToResortAdConverter = context -> {
            ResortAdCreationDTO source = context.getSource();
            ResortAd destination = context.getDestination();

            destination.setTags(new HashSet<Tag>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);

            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));

            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        modelMapper.createTypeMap(AdventureAdCreationDTO.class, AdventureAd.class).setPostConverter(CreationDtoToAdventureAdConverter);
        modelMapper.createTypeMap(AdventureAdUpdationDTO.class, AdventureAd.class).setPostConverter(UpdationDtoToAdventureAdConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdventureAdDisplayDTO.class).setPostConverter(AdventureAdToDisplayDtoConverter);
        modelMapper.createTypeMap(Photo.class, PhotoBriefDisplayDTO.class).setPostConverter(PhotoToDisplayDtoConverter);
        modelMapper.createTypeMap(Photo.class, PhotoUploadDisplayDTO.class).setPostConverter(PhotoToUploadDisplayDtoConverter);
        modelMapper.createTypeMap(Customer.class, CustomerDisplayDTO.class);
        modelMapper.createTypeMap(BoatAdCreationDTO.class, BoatAd.class).setPostConverter(CreationDtoToBoatAdConverter);
        modelMapper.createTypeMap(BoatAd.class, BoatAdDisplayDTO.class).setPostConverter(BoatAdToDisplayDtoConverter);
        modelMapper.createTypeMap(RegistrationRequestCreationDTO.class, RegistrationRequest.class).setPostConverter(RegistrationRequestCreationDTOToRegistrationRequest);
        modelMapper.createTypeMap(ResortAdCreationDTO.class, ResortAd.class).setPostConverter(CreationDtoToResortAdConverter);
    }
}
