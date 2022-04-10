package com.team4.isamrs.controller;

import com.team4.isamrs.dto.creation.AdventureAdCreationDTO;
import com.team4.isamrs.dto.creation.HourlyPriceCreationDTO;
import com.team4.isamrs.dto.display.AdventureAdDisplayDTO;
import com.team4.isamrs.dto.display.HourlyPriceDisplayDTO;
import com.team4.isamrs.dto.updation.AdventureAdUpdationDTO;
import com.team4.isamrs.model.adventure.AdventureAd;
import com.team4.isamrs.model.advertisement.HourlyPrice;
import com.team4.isamrs.service.AdventureAdService;
import com.team4.isamrs.service.HourlyPriceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ads/adventures")
@CrossOrigin(origins = "*")
public class AdventureController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdventureAdService adventureAdService;

    @Autowired
    private HourlyPriceService hourlyPriceService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdventureAdDisplayDTO>> findAllAdventureAds() {
        Collection<AdventureAd> adventureAds = adventureAdService.findAll();

        Collection<AdventureAdDisplayDTO> dto = adventureAds.stream()
                .map(e -> modelMapper.map(e, AdventureAdDisplayDTO.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdventureAdDisplayDTO> findAdventureAdById(@PathVariable Long id) {
        Optional<AdventureAd> adventureAd = adventureAdService.findById(id);

        if (adventureAd.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AdventureAdDisplayDTO dto = modelMapper.map(adventureAd.get(), AdventureAdDisplayDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.FOUND);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> addAdventureAd(@Valid @RequestBody AdventureAdCreationDTO dto) throws URISyntaxException {
        AdventureAd adventureAd = modelMapper.map(dto, AdventureAd.class);

        Long id = adventureAdService.createAdventureAd(adventureAd);
        if (id == null)
            return ResponseEntity.internalServerError().build();
        String uri = "/ads/adventures/" + id;
        return ResponseEntity.created(new URI(uri)).body("New AdventureAd created at: " + uri);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAdventureAd(@PathVariable Long id, @Valid @RequestBody AdventureAdUpdationDTO dto) {
        Optional<AdventureAd> adventureAd = adventureAdService.findById(id);

        if (adventureAd.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!adventureAdService.updateAdventureAd(adventureAd.get(), dto))
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok("AdventureAd updated.");
    }

    @GetMapping(value = "/{id}/prices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<HourlyPriceDisplayDTO>> getPrices(@PathVariable Long id) {
        Optional<AdventureAd> adventureAd = adventureAdService.findById(id);
        if (adventureAd.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Collection<HourlyPrice> prices = adventureAd.get().getPrices();
        Collection<HourlyPriceDisplayDTO> dto = prices.stream()
                .map(e -> modelMapper.map(e, HourlyPriceDisplayDTO.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/prices")
    public ResponseEntity<?> addPrice(@PathVariable Long id, @Valid @RequestBody HourlyPriceCreationDTO dto) {
        Optional<AdventureAd> adventureAd = adventureAdService.findById(id);
        if (adventureAd.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        HourlyPrice hourlyPrice = modelMapper.map(dto, HourlyPrice.class);
        if (!adventureAdService.addPriceToAdventureAd(hourlyPrice, adventureAd.get()))
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok().body("Price created.");
    }

    @DeleteMapping(value = "/{advertisementId}/prices/{priceId}")
    public ResponseEntity<?> removePrice(@PathVariable Long advertisementId, @PathVariable Long priceId) {
        Optional<AdventureAd> adventureAd = adventureAdService.findById(advertisementId);
        Optional<HourlyPrice> hourlyPrice = hourlyPriceService.findById(priceId);

        if (adventureAd.isEmpty() || hourlyPrice.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!adventureAdService.removePrice(hourlyPrice.get(), adventureAd.get()))
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok().body("Price deleted.");
    }

    @PutMapping(value = "/{advertisementId}/prices/{priceId}")
    public ResponseEntity<?> updatePrice(@PathVariable Long advertisementId, @PathVariable Long priceId, @Valid @RequestBody HourlyPriceCreationDTO dto) {
        Optional<AdventureAd> adventureAd = adventureAdService.findById(advertisementId);
        Optional<HourlyPrice> hourlyPrice = hourlyPriceService.findById(priceId);

        if (adventureAd.isEmpty() || hourlyPrice.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!adventureAdService.updatePrice(hourlyPrice.get(), adventureAd.get(), dto))
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok().body("Price updated.");
    }
}
