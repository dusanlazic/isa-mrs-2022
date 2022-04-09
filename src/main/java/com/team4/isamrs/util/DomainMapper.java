package com.team4.isamrs.util;

import com.team4.isamrs.dto.AdventureAdCreationDTO;
import com.team4.isamrs.dto.AdventureAdDisplayDTO;
import com.team4.isamrs.model.entity.adventure.AdventureAd;
import com.team4.isamrs.model.entity.advertisement.Tag;
import com.team4.isamrs.repository.AdventureAdRepository;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class DomainMapper {

    @Autowired
    private AdventureAdRepository adventureAdRepository;

    @Autowired
    private TagRepository tagRepository;

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

            source.getTagIds().forEach(id -> destination.addTag(tagRepository.findById(id).get()));

            return destination;
        };

        Converter<AdventureAd, AdventureAdDisplayDTO> AdventureAdToDisplayDtoConverter = context -> {
            AdventureAd source = context.getSource();
            AdventureAdDisplayDTO destination = context.getDestination();

            destination.setTags(source.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
            destination.setPhotos(new HashSet<>());

            return destination;
        };

        modelMapper.createTypeMap(AdventureAdCreationDTO.class, AdventureAd.class).setPostConverter(CreationDtoToAdventureAdConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdventureAdDisplayDTO.class).setPostConverter(AdventureAdToDisplayDtoConverter);
    }
}
