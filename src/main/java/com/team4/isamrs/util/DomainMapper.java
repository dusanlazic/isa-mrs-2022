package com.team4.isamrs.util;

import com.team4.isamrs.dto.AdventureAdCreationDTO;
import com.team4.isamrs.dto.AdventureAdDisplayDTO;
import com.team4.isamrs.dto.PhotoDisplayDTO;
import com.team4.isamrs.model.entity.adventure.AdventureAd;
import com.team4.isamrs.model.entity.advertisement.Photo;
import com.team4.isamrs.model.entity.advertisement.Tag;
import com.team4.isamrs.repository.AdventureAdRepository;
import com.team4.isamrs.repository.FishingEquipmentRepository;
import com.team4.isamrs.repository.PhotoRepository;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
            source.getTagIds().forEach(id -> destination.addTag(tagRepository.findById(id).get()));
            source.getFishingEquipmentIds().forEach(id -> destination.addFishingEquipment(fishingEquipmentRepository.findById(id).get()));
            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));

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

        Converter<Photo, PhotoDisplayDTO> PhotoToDisplayDtoConverter = context -> {
            context.getDestination().setUri("/photos/" + context.getSource().getId().toString());
            return context.getDestination();
        };

        modelMapper.createTypeMap(AdventureAdCreationDTO.class, AdventureAd.class).setPostConverter(CreationDtoToAdventureAdConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdventureAdDisplayDTO.class).setPostConverter(AdventureAdToDisplayDtoConverter);
        modelMapper.createTypeMap(Photo.class, PhotoDisplayDTO.class).setPostConverter(PhotoToDisplayDtoConverter);
    }
}
