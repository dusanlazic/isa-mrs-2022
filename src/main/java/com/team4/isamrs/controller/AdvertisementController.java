package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.OptionCreationDTO;
import com.team4.isamrs.dto.display.OptionDisplayDTO;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.Option;
import com.team4.isamrs.service.AdvertisementService;
import com.team4.isamrs.service.OptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ads")
@CrossOrigin(origins = "*")
public class AdvertisementController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private OptionService optionService;

    @GetMapping(value = "/{id}/options", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OptionDisplayDTO>> getOptions(@PathVariable Long id) {
        Optional<Advertisement> advertisement = advertisementService.findById(id);
        if (advertisement.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Collection<Option> options = advertisement.get().getOptions();
        Collection<OptionDisplayDTO> dto = options.stream()
                .map(e -> modelMapper.map(e, OptionDisplayDTO.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/options")
    public ResponseEntity<?> addOption(@PathVariable Long id, @Valid @RequestBody OptionCreationDTO dto) {
        Optional<Advertisement> advertisement = advertisementService.findById(id);
        if (advertisement.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Option option = modelMapper.map(dto, Option.class);
        if (!advertisementService.addOptionToAdvertisement(option, advertisement.get()))
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok().body("Option created.");
    }

    @DeleteMapping(value = "/{advertisementId}/options/{optionId}")
    public ResponseEntity<?> removeOption(@PathVariable Long advertisementId, @PathVariable Long optionId) {
        Optional<Advertisement> advertisement = advertisementService.findById(advertisementId);
        Optional<Option> option = optionService.findById(optionId);

        if (advertisement.isEmpty() || option.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!advertisementService.removeOption(option.get(), advertisement.get()))
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok().body("Option deleted.");
    }

    @PutMapping(value = "/{advertisementId}/options/{optionId}")
    public ResponseEntity<?> updateOption(@PathVariable Long advertisementId, @PathVariable Long optionId, @Valid @RequestBody OptionCreationDTO dto) {
        Optional<Advertisement> advertisement = advertisementService.findById(advertisementId);
        Optional<Option> option = optionService.findById(optionId);

        if (advertisement.isEmpty() || option.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!advertisementService.updateOption(option.get(), advertisement.get(), dto))
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok().body("Option updated.");
    }
}
