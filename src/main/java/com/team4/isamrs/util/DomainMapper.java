package com.team4.isamrs.util;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.creation.RegistrationRequestCreationDTO;
import com.team4.isamrs.dto.creation.ResortAdCreationDTO;
import com.team4.isamrs.dto.display.*;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.dto.updation.BoatAdUpdationDTO;
import com.team4.isamrs.dto.updation.OptionUpdationDTO;
import com.team4.isamrs.dto.updation.ResortAdUpdationDTO;
import com.team4.isamrs.model.advertisement.*;
import com.team4.isamrs.model.enumeration.AccountType;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.user.*;
import com.team4.isamrs.repository.FishingEquipmentRepository;
import com.team4.isamrs.repository.NavigationalEquipmentRepository;
import com.team4.isamrs.repository.PhotoRepository;
import com.team4.isamrs.repository.TagRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DomainMapper {

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

            // Lookup names and fill the collections
            destination.setTags(new HashSet<Tag>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);
            destination.setFishingEquipment(new HashSet<FishingEquipment>());
            mapFishingEquipmentNames(source.getFishingEquipmentNames()).forEach(destination::addFishingEquipment);

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
            destination.setPhotos(new ArrayList<>());
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

            // Sync bidirectional relationships
            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            destination.setTags(new HashSet<Tag>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);
            destination.setFishingEquipment(new HashSet<FishingEquipment>());
            mapFishingEquipmentNames(source.getFishingEquipmentNames()).forEach(destination::addFishingEquipment);

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

        Converter<BoatAdCreationDTO, BoatAd> CreationDtoToBoatAdConverter = context -> {
            BoatAdCreationDTO source = context.getSource();
            BoatAd destination = context.getDestination();

            destination.setTags(new HashSet<Tag>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);
            destination.setFishingEquipment(new HashSet<FishingEquipment>());
            mapFishingEquipmentNames(source.getFishingEquipmentNames()).forEach(destination::addFishingEquipment);
            destination.setNavigationalEquipment(new HashSet<NavigationalEquipment>());
            mapNavigationalEquipmentNames(source.getNavigationalEquipmentNames()).forEach(destination::addNavigationalEquipment);

            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));

            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        Converter<BoatAd, BoatAdDisplayDTO> BoatAdToDisplayDtoConverter = context -> {
            BoatAd source = context.getSource();
            BoatAdDisplayDTO destination = context.getDestination();

            destination.setTags(source.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

            return destination;
        };

        Converter<BoatAdUpdationDTO, BoatAd> UpdationDtoToBoatAdConverter = context -> {
            BoatAdUpdationDTO source = context.getSource();
            BoatAd destination = context.getDestination();

            destination.setPhotos(new ArrayList<>());
            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));

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

            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            destination.setTags(new HashSet<Tag>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);
            destination.setNavigationalEquipment(new HashSet<NavigationalEquipment>());
            mapNavigationalEquipmentNames(source.getNavigationalEquipmentNames()).forEach(destination::addNavigationalEquipment);
            destination.setFishingEquipment(new HashSet<FishingEquipment>());
            mapFishingEquipmentNames(source.getFishingEquipmentNames()).forEach(destination::addFishingEquipment);

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
            destination.setNumberOfBeds(source.getBedCountPerRoom().stream().map(Object::toString).collect(Collectors.joining(",")));

            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        Converter<ResortAd, ResortAdDisplayDTO> ResortAdToDisplayDtoConverter = context -> {
            ResortAd source = context.getSource();
            ResortAdDisplayDTO destination = context.getDestination();

            destination.setTags(source.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
            destination.setBedCountPerRoom(Arrays.stream(source.getNumberOfBeds().split(",")).map(Integer::parseInt).collect(Collectors.toList()));

            return destination;
        };

        Converter<ResortAd, ResortAdSimpleDisplayDTO> ResortAdToSimpleDisplayDtoConverter = context -> {
            ResortAd source = context.getSource();
            ResortAdSimpleDisplayDTO destination = context.getDestination();

            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                    PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<BoatAd, BoatAdSimpleDisplayDTO> BoatAdToSimpleDisplayDtoConverter = context -> {
            BoatAd source = context.getSource();
            BoatAdSimpleDisplayDTO destination = context.getDestination();

            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<AdventureAd, AdventureAdSimpleDisplayDTO> AdventureAdToSimpleDisplayDtoConverter = context -> {
            AdventureAd source = context.getSource();
            AdventureAdSimpleDisplayDTO destination = context.getDestination();

            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<ResortAd, AdvertisementSimpleDisplayDTO> ResortAdToAdvertisementSimpleDisplayDtoConverter = context -> {
            ResortAd source = context.getSource();
            AdvertisementSimpleDisplayDTO destination = context.getDestination();
            destination.setAdvertisementType("resort");
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<BoatAd, AdvertisementSimpleDisplayDTO> BoatAdToAdvertisementSimpleDisplayDtoConverter = context -> {
            BoatAd source = context.getSource();
            AdvertisementSimpleDisplayDTO destination = context.getDestination();
            destination.setAdvertisementType("boat");
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<AdventureAd, AdvertisementSimpleDisplayDTO> AdventureAdToAdvertisementSimpleDisplayDtoConverter = context -> {
            AdventureAd source = context.getSource();
            AdvertisementSimpleDisplayDTO destination = context.getDestination();
            destination.setAdvertisementType("adventure");
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };


        Converter<ResortAdUpdationDTO, ResortAd> UpdationDtoToResortAdConverter = context -> {
            ResortAdUpdationDTO source = context.getSource();
            ResortAd destination = context.getDestination();

            destination.setPhotos(new ArrayList<>());
            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));
            destination.setNumberOfBeds(source.getBedCountPerRoom().stream().map(Object::toString).collect(Collectors.joining(",")));

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

            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            destination.setTags(new HashSet<Tag>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);

            return destination;
        };

        Converter<Advertiser, SessionDisplayDTO> AdvertiserToSessionDisplayDtoConverter = context -> {
            User source = context.getSource();
            SessionDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));

            return destination;
        };

        Converter<Administrator, SessionDisplayDTO> AdministratorToSessionDisplayDtoConverter = context -> {
            User source = context.getSource();
            SessionDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));

            return destination;
        };

        Converter<Customer, SessionDisplayDTO> CustomerToSessionDisplayDtoConverter = context -> {
            User source = context.getSource();
            SessionDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));

            return destination;
        };

        Converter<User, SessionDisplayDTO> UserToSessionDisplayDtoConverter = context -> {
            User source = context.getSource();
            SessionDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));

            return destination;
        };

        Converter<Advertiser, AccountDisplayDTO> AdvertiserToAccountDisplayDtoConverter = context -> {
            User source = context.getSource();
            AccountDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));
            destination.setAvatar(modelMapper.map(source.getAvatar(), PhotoBriefDisplayDTO.class));
            return destination;
        };

        Converter<Administrator, AccountDisplayDTO> AdministratorToAccountDisplayDtoConverter = context -> {
            User source = context.getSource();
            AccountDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));
            destination.setAvatar(modelMapper.map(source.getAvatar(), PhotoBriefDisplayDTO.class));
            return destination;
        };

        Converter<Customer, AccountDisplayDTO> CustomerToAccountDisplayDtoConverter = context -> {
            User source = context.getSource();
            AccountDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));
            destination.setAvatar(modelMapper.map(source.getAvatar(), PhotoBriefDisplayDTO.class));
            return destination;
        };

        Converter<User, AccountDisplayDTO> UserToAccountDisplayDtoConverter = context -> {
            User source = context.getSource();
            AccountDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));
            destination.setAvatar(modelMapper.map(source.getAvatar(), PhotoBriefDisplayDTO.class));
            return destination;
        };

        Converter<RemovalRequest, RemovalRequestDisplayDTO> RemovalRequestToDisplayDtoConverter = context -> {
            RemovalRequest source = context.getSource();
            RemovalRequestDisplayDTO destination = context.getDestination();

            destination.setUser(modelMapper.map(source.getUser(), SessionDisplayDTO.class));

            return destination;
        };

        Converter<Customer, CustomerPublicDisplayDTO> CustomerToPublicDisplayDtoConverter = context -> {
            context.getDestination().setAvatar("/photos/" + context.getSource().getAvatar().getStoredFilename());
            return context.getDestination();
        };

        modelMapper.createTypeMap(AdventureAdCreationDTO.class, AdventureAd.class).setPostConverter(CreationDtoToAdventureAdConverter);
        modelMapper.createTypeMap(AdventureAdUpdationDTO.class, AdventureAd.class).setPostConverter(UpdationDtoToAdventureAdConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdventureAdDisplayDTO.class).setPostConverter(AdventureAdToDisplayDtoConverter);
        modelMapper.createTypeMap(Photo.class, PhotoBriefDisplayDTO.class).setPostConverter(PhotoToDisplayDtoConverter);
        modelMapper.createTypeMap(BoatAdCreationDTO.class, BoatAd.class).setPostConverter(CreationDtoToBoatAdConverter);
        modelMapper.createTypeMap(BoatAdUpdationDTO.class, BoatAd.class).setPostConverter(UpdationDtoToBoatAdConverter);
        modelMapper.createTypeMap(BoatAd.class, BoatAdDisplayDTO.class).setPostConverter(BoatAdToDisplayDtoConverter);
        modelMapper.createTypeMap(RegistrationRequestCreationDTO.class, RegistrationRequest.class).setPostConverter(RegistrationRequestCreationDTOToRegistrationRequest);
        modelMapper.createTypeMap(ResortAdCreationDTO.class, ResortAd.class).setPostConverter(CreationDtoToResortAdConverter);
        modelMapper.createTypeMap(ResortAdUpdationDTO.class, ResortAd.class).setPostConverter(UpdationDtoToResortAdConverter);
        modelMapper.createTypeMap(ResortAd.class, ResortAdDisplayDTO.class).setPostConverter(ResortAdToDisplayDtoConverter);

        modelMapper.createTypeMap(Advertiser.class, SessionDisplayDTO.class).setPostConverter(AdvertiserToSessionDisplayDtoConverter);
        modelMapper.createTypeMap(Administrator.class, SessionDisplayDTO.class).setPostConverter(AdministratorToSessionDisplayDtoConverter);
        modelMapper.createTypeMap(Customer.class, SessionDisplayDTO.class).setPostConverter(CustomerToSessionDisplayDtoConverter);
        modelMapper.createTypeMap(Customer.class, CustomerPublicDisplayDTO.class).setPostConverter(CustomerToPublicDisplayDtoConverter);
        modelMapper.createTypeMap(User.class, SessionDisplayDTO.class).setPostConverter(UserToSessionDisplayDtoConverter);
        modelMapper.createTypeMap(Advertiser.class, AccountDisplayDTO.class).setPostConverter(AdvertiserToAccountDisplayDtoConverter);
        modelMapper.createTypeMap(Administrator.class, AccountDisplayDTO.class).setPostConverter(AdministratorToAccountDisplayDtoConverter);
        modelMapper.createTypeMap(Customer.class, AccountDisplayDTO.class).setPostConverter(CustomerToAccountDisplayDtoConverter);
        modelMapper.createTypeMap(User.class, AccountDisplayDTO.class).setPostConverter(UserToAccountDisplayDtoConverter);

        modelMapper.createTypeMap(RemovalRequest.class, RemovalRequestDisplayDTO.class).setPostConverter(RemovalRequestToDisplayDtoConverter);
        modelMapper.createTypeMap(ResortAd.class, ResortAdSimpleDisplayDTO.class).setPostConverter(ResortAdToSimpleDisplayDtoConverter);
        modelMapper.createTypeMap(BoatAd.class, BoatAdSimpleDisplayDTO.class).setPostConverter(BoatAdToSimpleDisplayDtoConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdventureAdSimpleDisplayDTO.class).setPostConverter(AdventureAdToSimpleDisplayDtoConverter);

        modelMapper.createTypeMap(ResortAd.class, AdvertisementSimpleDisplayDTO.class).setPostConverter(ResortAdToAdvertisementSimpleDisplayDtoConverter);
        modelMapper.createTypeMap(BoatAd.class, AdvertisementSimpleDisplayDTO.class).setPostConverter(BoatAdToAdvertisementSimpleDisplayDtoConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdvertisementSimpleDisplayDTO.class).setPostConverter(AdventureAdToAdvertisementSimpleDisplayDtoConverter);
    }

    private HashSet<Tag> mapTagNames(Set<String> tagNames) {
        HashSet<Tag> newSet = new HashSet<>();
        tagNames.forEach(name -> {
            Optional<Tag> tag = tagRepository.findByNameIgnoreCase(name);
            if (tag.isPresent()) {
                newSet.add(tag.get());
            } else {
                newSet.add(new Tag(name));
            }
        });
        return newSet;
    }

    private HashSet<FishingEquipment> mapFishingEquipmentNames(Set<String> fishingEquipmentNames) {
        HashSet<FishingEquipment> newSet = new HashSet<>();
        fishingEquipmentNames.forEach(name -> {
            Optional<FishingEquipment> fishingEquipment = fishingEquipmentRepository.findByNameIgnoreCase(name);
            if (fishingEquipment.isPresent()) {
                newSet.add(fishingEquipment.get());
            } else {
                newSet.add(new FishingEquipment(name));
            }
        });
        return newSet;
    }

    private HashSet<NavigationalEquipment> mapNavigationalEquipmentNames(Set<String> navigationalEquipmentNames) {
        HashSet<NavigationalEquipment> newSet = new HashSet<>();
        navigationalEquipmentNames.forEach(name -> {
            Optional<NavigationalEquipment> navigationalEquipment = navigationalEquipmentRepository.findByNameIgnoreCase(name);
            if (navigationalEquipment.isPresent()) {
                newSet.add(navigationalEquipment.get());
            } else {
                newSet.add(new NavigationalEquipment(name));
            }
        });
        return newSet;
    }
}
