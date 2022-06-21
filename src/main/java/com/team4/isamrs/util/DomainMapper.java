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
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.*;
import com.team4.isamrs.repository.*;
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
    private AdvertisementRepository advertisementRepository;

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
        Converter<AdventureAdCreationDTO, AdventureAd> creationDtoToAdventureAdConverter = context -> {
            AdventureAdCreationDTO source = context.getSource();
            AdventureAd destination = context.getDestination();

            // Lookup names and fill the collections
            destination.setTags(new HashSet<>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);
            destination.setFishingEquipment(new HashSet<>());
            mapFishingEquipmentNames(source.getFishingEquipmentNames()).forEach(destination::addFishingEquipment);

            // Lookup IDs and fill the collections
            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));

            // Sync bidirectional relationships
            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        Converter<AdventureAdUpdationDTO, AdventureAd> updationDtoToAdventureAdConverter = context -> {
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

            destination.setTags(new HashSet<>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);
            destination.setFishingEquipment(new HashSet<>());
            mapFishingEquipmentNames(source.getFishingEquipmentNames()).forEach(destination::addFishingEquipment);

            return destination;
        };

        Converter<AdventureAd, AdventureAdDisplayDTO> adventureAdToDisplayDtoConverter = context -> {
            AdventureAd source = context.getSource();
            AdventureAdDisplayDTO destination = context.getDestination();

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setTags(source.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

            return destination;
        };

        Converter<Photo, PhotoBriefDisplayDTO> photoToDisplayDtoConverter = context -> {
            context.getDestination().setUri("/photos/" + context.getSource().getStoredFilename());
            return context.getDestination();
        };

        Converter<BoatAdCreationDTO, BoatAd> creationDtoToBoatAdConverter = context -> {
            BoatAdCreationDTO source = context.getSource();
            BoatAd destination = context.getDestination();

            destination.setTags(new HashSet<>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);
            destination.setFishingEquipment(new HashSet<>());
            mapFishingEquipmentNames(source.getFishingEquipmentNames()).forEach(destination::addFishingEquipment);
            destination.setNavigationalEquipment(new HashSet<>());
            mapNavigationalEquipmentNames(source.getNavigationalEquipmentNames()).forEach(destination::addNavigationalEquipment);

            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));

            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        Converter<BoatAd, BoatAdDisplayDTO> boatAdToDisplayDtoConverter = context -> {
            BoatAd source = context.getSource();
            BoatAdDisplayDTO destination = context.getDestination();

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setTags(source.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

            return destination;
        };

        Converter<BoatAdUpdationDTO, BoatAd> updationDtoToBoatAdConverter = context -> {
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

            destination.setTags(new HashSet<>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);
            destination.setNavigationalEquipment(new HashSet<>());
            mapNavigationalEquipmentNames(source.getNavigationalEquipmentNames()).forEach(destination::addNavigationalEquipment);
            destination.setFishingEquipment(new HashSet<>());
            mapFishingEquipmentNames(source.getFishingEquipmentNames()).forEach(destination::addFishingEquipment);

            return destination;
        };

        Converter<RegistrationRequestCreationDTO, RegistrationRequest> registrationRequestCreationDTOToRegistrationRequest = context -> {
            RegistrationRequestCreationDTO source = context.getSource();
            RegistrationRequest destination = context.getDestination();

            destination.setAccountType(AccountType.values()[source.getAccountType()]);
            destination.setApprovalStatus(ApprovalStatus.PENDING);

            return destination;
        };

        Converter<ResortAdCreationDTO, ResortAd> creationDtoToResortAdConverter = context -> {
            ResortAdCreationDTO source = context.getSource();
            ResortAd destination = context.getDestination();

            destination.setTags(new HashSet<>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);

            source.getPhotoIds().forEach(id -> destination.addPhoto(photoRepository.findById(id).get()));
            destination.setNumberOfBeds(source.getBedCountPerRoom().stream().map(Object::toString).collect(Collectors.joining(",")));

            destination.getOptions().forEach(e -> e.setAdvertisement(destination));

            return destination;
        };

        Converter<ResortAd, ResortAdDisplayDTO> resortAdToDisplayDtoConverter = context -> {
            ResortAd source = context.getSource();
            ResortAdDisplayDTO destination = context.getDestination();

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setTags(source.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
            destination.setBedCountPerRoom(Arrays.stream(source.getNumberOfBeds().split(",")).map(Integer::parseInt).collect(Collectors.toList()));

            return destination;
        };

        Converter<ResortAd, ResortAdSimpleDisplayDTO> resortAdToSimpleDisplayDtoConverter = context -> {
            ResortAd source = context.getSource();
            ResortAdSimpleDisplayDTO destination = context.getDestination();

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                    PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<BoatAd, BoatAdSimpleDisplayDTO> boatAdToSimpleDisplayDtoConverter = context -> {
            BoatAd source = context.getSource();
            BoatAdSimpleDisplayDTO destination = context.getDestination();

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<AdventureAd, AdventureAdSimpleDisplayDTO> adventureAdToSimpleDisplayDtoConverter = context -> {
            AdventureAd source = context.getSource();
            AdventureAdSimpleDisplayDTO destination = context.getDestination();

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<ResortAd, AdvertisementSimpleDisplayDTO> resortAdToAdvertisementSimpleDisplayDtoConverter = context -> {
            ResortAd source = context.getSource();
            AdvertisementSimpleDisplayDTO destination = context.getDestination();
            destination.setAdvertisementType("resort");

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<BoatAd, AdvertisementSimpleDisplayDTO> boatAdToAdvertisementSimpleDisplayDtoConverter = context -> {
            BoatAd source = context.getSource();
            AdvertisementSimpleDisplayDTO destination = context.getDestination();
            destination.setAdvertisementType("boat");

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<AdventureAd, AdvertisementSimpleDisplayDTO> adventureAdToAdvertisementSimpleDisplayDtoConverter = context -> {
            AdventureAd source = context.getSource();
            AdvertisementSimpleDisplayDTO destination = context.getDestination();
            destination.setAdvertisementType("adventure");

            Optional<Double> averageRating = advertisementRepository.findAverageRatingForAdvertisement(source);
            if (averageRating.isPresent())
                destination.setAverageRating(Math.round(averageRating.get() * 100.0) / 100.0);
            else
                destination.setAverageRating(null);
            destination.setPhoto(source.getPhotos().isEmpty() ?
                    null :
                    modelMapper.map(source.getPhotos().stream().findFirst().orElse(null),
                            PhotoBriefDisplayDTO.class));

            return destination;
        };

        Converter<ResortAdUpdationDTO, ResortAd> updationDtoToResortAdConverter = context -> {
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

            destination.setTags(new HashSet<>());
            mapTagNames(source.getTagNames()).forEach(destination::addTag);

            return destination;
        };

        Converter<Advertiser, SessionDisplayDTO> advertiserToSessionDisplayDtoConverter = context -> {
            User source = context.getSource();
            SessionDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));

            return destination;
        };

        Converter<Administrator, SessionDisplayDTO> administratorToSessionDisplayDtoConverter = context -> {
            User source = context.getSource();
            SessionDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));

            return destination;
        };

        Converter<Customer, SessionDisplayDTO> customerToSessionDisplayDtoConverter = context -> {
            User source = context.getSource();
            SessionDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));

            return destination;
        };

        Converter<User, SessionDisplayDTO> userToSessionDisplayDtoConverter = context -> {
            User source = context.getSource();
            SessionDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));

            return destination;
        };

        Converter<Advertiser, AccountDisplayDTO> advertiserToAccountDisplayDtoConverter = context -> {
            User source = context.getSource();
            AccountDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));
            destination.setAvatar(safeMapAvatar(source.getAvatar(), modelMapper));
            return destination;
        };

        Converter<Administrator, AccountDisplayDTO> administratorToAccountDisplayDtoConverter = context -> {
            User source = context.getSource();
            AccountDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));
            destination.setAvatar(safeMapAvatar(source.getAvatar(), modelMapper));
            return destination;
        };

        Converter<Customer, AccountDisplayDTO> customerToAccountDisplayDtoConverter = context -> {
            User source = context.getSource();
            AccountDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));
            destination.setAvatar(safeMapAvatar(source.getAvatar(), modelMapper));
            return destination;
        };

        Converter<User, AccountDisplayDTO> userToAccountDisplayDtoConverter = context -> {
            User source = context.getSource();
            AccountDisplayDTO destination = context.getDestination();

            destination.setAccountType(source.getAuthorities().stream().findFirst().get().getName().substring(5));
            destination.setAvatar(safeMapAvatar(source.getAvatar(), modelMapper));
            return destination;
        };

        Converter<RemovalRequest, RemovalRequestDisplayDTO> removalRequestToDisplayDtoConverter = context -> {
            RemovalRequest source = context.getSource();
            RemovalRequestDisplayDTO destination = context.getDestination();

            destination.setUser(modelMapper.map(source.getUser(), SessionDisplayDTO.class));

            return destination;
        };

        Converter<Customer, CustomerPublicDisplayDTO> customerToPublicDisplayDtoConverter = context -> {
            User source = context.getSource();
            CustomerPublicDisplayDTO destination = context.getDestination();
            destination.setAvatar(safeMapAvatar(source.getAvatar(), modelMapper));
            return destination;
        };

        Converter<Review, ReviewPublicDisplayDTO> reviewToReviewPublicDisplayDtoConverter = context -> {
            Review source = context.getSource();
            ReviewPublicDisplayDTO destination = context.getDestination();
            destination.setCustomer(modelMapper.map(source.getCustomer(), CustomerPublicDisplayDTO.class));
            return destination;
        };

        modelMapper.createTypeMap(AdventureAdCreationDTO.class, AdventureAd.class).setPostConverter(creationDtoToAdventureAdConverter);
        modelMapper.createTypeMap(AdventureAdUpdationDTO.class, AdventureAd.class).setPostConverter(updationDtoToAdventureAdConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdventureAdDisplayDTO.class).setPostConverter(adventureAdToDisplayDtoConverter);
        modelMapper.createTypeMap(Photo.class, PhotoBriefDisplayDTO.class).setPostConverter(photoToDisplayDtoConverter);
        modelMapper.createTypeMap(BoatAdCreationDTO.class, BoatAd.class).setPostConverter(creationDtoToBoatAdConverter);
        modelMapper.createTypeMap(BoatAdUpdationDTO.class, BoatAd.class).setPostConverter(updationDtoToBoatAdConverter);
        modelMapper.createTypeMap(BoatAd.class, BoatAdDisplayDTO.class).setPostConverter(boatAdToDisplayDtoConverter);
        modelMapper.createTypeMap(RegistrationRequestCreationDTO.class, RegistrationRequest.class).setPostConverter(registrationRequestCreationDTOToRegistrationRequest);
        modelMapper.createTypeMap(ResortAdCreationDTO.class, ResortAd.class).setPostConverter(creationDtoToResortAdConverter);
        modelMapper.createTypeMap(ResortAdUpdationDTO.class, ResortAd.class).setPostConverter(updationDtoToResortAdConverter);
        modelMapper.createTypeMap(ResortAd.class, ResortAdDisplayDTO.class).setPostConverter(resortAdToDisplayDtoConverter);

        modelMapper.createTypeMap(Advertiser.class, SessionDisplayDTO.class).setPostConverter(advertiserToSessionDisplayDtoConverter);
        modelMapper.createTypeMap(Administrator.class, SessionDisplayDTO.class).setPostConverter(administratorToSessionDisplayDtoConverter);
        modelMapper.createTypeMap(Customer.class, SessionDisplayDTO.class).setPostConverter(customerToSessionDisplayDtoConverter);
        modelMapper.createTypeMap(Customer.class, CustomerPublicDisplayDTO.class).setPostConverter(customerToPublicDisplayDtoConverter);
        modelMapper.createTypeMap(User.class, SessionDisplayDTO.class).setPostConverter(userToSessionDisplayDtoConverter);
        modelMapper.createTypeMap(Advertiser.class, AccountDisplayDTO.class).setPostConverter(advertiserToAccountDisplayDtoConverter);
        modelMapper.createTypeMap(Administrator.class, AccountDisplayDTO.class).setPostConverter(administratorToAccountDisplayDtoConverter);
        modelMapper.createTypeMap(Customer.class, AccountDisplayDTO.class).setPostConverter(customerToAccountDisplayDtoConverter);
        modelMapper.createTypeMap(User.class, AccountDisplayDTO.class).setPostConverter(userToAccountDisplayDtoConverter);

        modelMapper.createTypeMap(RemovalRequest.class, RemovalRequestDisplayDTO.class).setPostConverter(removalRequestToDisplayDtoConverter);
        modelMapper.createTypeMap(ResortAd.class, ResortAdSimpleDisplayDTO.class).setPostConverter(resortAdToSimpleDisplayDtoConverter);
        modelMapper.createTypeMap(BoatAd.class, BoatAdSimpleDisplayDTO.class).setPostConverter(boatAdToSimpleDisplayDtoConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdventureAdSimpleDisplayDTO.class).setPostConverter(adventureAdToSimpleDisplayDtoConverter);

        modelMapper.createTypeMap(ResortAd.class, AdvertisementSimpleDisplayDTO.class).setPostConverter(resortAdToAdvertisementSimpleDisplayDtoConverter);
        modelMapper.createTypeMap(BoatAd.class, AdvertisementSimpleDisplayDTO.class).setPostConverter(boatAdToAdvertisementSimpleDisplayDtoConverter);
        modelMapper.createTypeMap(AdventureAd.class, AdvertisementSimpleDisplayDTO.class).setPostConverter(adventureAdToAdvertisementSimpleDisplayDtoConverter);

        modelMapper.createTypeMap(Review.class, ReviewPublicDisplayDTO.class).setPostConverter(reviewToReviewPublicDisplayDtoConverter);
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

    private PhotoBriefDisplayDTO safeMapAvatar(Photo photo, ModelMapper modelMapper) {
        if (photo != null)
            return modelMapper.map(photo, PhotoBriefDisplayDTO.class);

        return new PhotoBriefDisplayDTO(null);
    }
}
