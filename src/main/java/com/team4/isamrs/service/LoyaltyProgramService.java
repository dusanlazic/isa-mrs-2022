package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.LoyaltyProgramCategoryBriefDisplayDTO;
import com.team4.isamrs.dto.display.LoyaltyProgramCategoryDetailedDisplayDTO;
import com.team4.isamrs.dto.display.LoyaltyProgramSettingsDisplayDTO;
import com.team4.isamrs.dto.display.PointsDisplayDTO;
import com.team4.isamrs.dto.updation.LoyaltyProgramCategoriesUpdationDTO;
import com.team4.isamrs.dto.updation.LoyaltyProgramCategoryUpdationDTO;
import com.team4.isamrs.dto.updation.LoyaltyProgramSettingsUpdationDTO;
import com.team4.isamrs.exception.NoSuchGlobalSettingException;
import com.team4.isamrs.model.config.GlobalSetting;
import com.team4.isamrs.model.loyalty.LoyaltyProgramCategory;
import com.team4.isamrs.model.loyalty.TargetedAccountType;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.model.user.User;
import com.team4.isamrs.repository.GlobalSettingRepository;
import com.team4.isamrs.repository.LoyaltyProgramCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoyaltyProgramService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GlobalSettingRepository globalSettingRepository;

    @Autowired
    private LoyaltyProgramCategoryRepository loyaltyProgramCategoryRepository;

    public void init() {
        GlobalSetting clientScorePerReservation = new GlobalSetting("clientScorePerReservation", "20");
        GlobalSetting advertiserScorePerReservation = new GlobalSetting("advertiserScorePerReservation", "10");

        globalSettingRepository.save(clientScorePerReservation);
        globalSettingRepository.save(advertiserScorePerReservation);

        LoyaltyProgramCategory cRegular = new LoyaltyProgramCategory(
                "Regular", "#000000",
                TargetedAccountType.CUSTOMER,
                0, 99,
                BigDecimal.ONE);

        LoyaltyProgramCategory cBronze = new LoyaltyProgramCategory(
                "Bronze", "#806f40",
                TargetedAccountType.CUSTOMER,
                100, 299,
                BigDecimal.valueOf(0.95));

        LoyaltyProgramCategory cSilver = new LoyaltyProgramCategory(
                "Silver", "#a6a6a6",
                TargetedAccountType.CUSTOMER,
                300, 499,
                BigDecimal.valueOf(0.9));

        LoyaltyProgramCategory cGold = new LoyaltyProgramCategory(
                "Gold", "#e6d222",
                TargetedAccountType.CUSTOMER,
                500, Integer.MAX_VALUE,
                BigDecimal.valueOf(0.8));

        LoyaltyProgramCategory aRegular = new LoyaltyProgramCategory(
                "Regular", "#000000",
                TargetedAccountType.ADVERTISER,
                0, 99,
                BigDecimal.ONE);

        LoyaltyProgramCategory aBronze = new LoyaltyProgramCategory(
                "Bronze", "#806f40",
                TargetedAccountType.ADVERTISER,
                100, 299,
                BigDecimal.valueOf(1.05));

        LoyaltyProgramCategory aSilver = new LoyaltyProgramCategory(
                "Silver", "#a6a6a6",
                TargetedAccountType.ADVERTISER,
                300, 499,
                BigDecimal.valueOf(1.1));

        LoyaltyProgramCategory aGold = new LoyaltyProgramCategory(
                "Gold", "#e6d222",
                TargetedAccountType.ADVERTISER,
                500, Integer.MAX_VALUE,
                BigDecimal.valueOf(1.2));

        loyaltyProgramCategoryRepository.save(cRegular);
        loyaltyProgramCategoryRepository.save(aRegular);
        loyaltyProgramCategoryRepository.save(cBronze);
        loyaltyProgramCategoryRepository.save(aBronze);
        loyaltyProgramCategoryRepository.save(cSilver);
        loyaltyProgramCategoryRepository.save(aSilver);
        loyaltyProgramCategoryRepository.save(cGold);
        loyaltyProgramCategoryRepository.save(aGold);
    }

    public PointsDisplayDTO getCurrentUsersPoints(Authentication auth) {
        User user = (User) auth.getPrincipal();

        Integer points = null;
        TargetedAccountType targetedAccountType = null;

        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            points = customer.getPoints();
            targetedAccountType = TargetedAccountType.CUSTOMER;
        } else if (user instanceof Advertiser) {
            Advertiser advertiser = (Advertiser) user;
            points = advertiser.getPoints();
            targetedAccountType = TargetedAccountType.ADVERTISER;
        }

        LoyaltyProgramCategory category = loyaltyProgramCategoryRepository.findByPointsAndAccountType(points, targetedAccountType).orElseThrow();

        return new PointsDisplayDTO(points, modelMapper.map(category, LoyaltyProgramCategoryBriefDisplayDTO.class));
    }

    public LoyaltyProgramSettingsDisplayDTO getSettings() {
        GlobalSetting clientScorePerReservation = globalSettingRepository.findByName("clientScorePerReservation").orElseThrow(NoSuchGlobalSettingException::new);
        GlobalSetting advertiserScorePerReservation = globalSettingRepository.findByName("advertiserScorePerReservation").orElseThrow(NoSuchGlobalSettingException::new);

        return new LoyaltyProgramSettingsDisplayDTO(clientScorePerReservation.getValue(), advertiserScorePerReservation.getValue());
    }

    public void updateSettings(LoyaltyProgramSettingsUpdationDTO dto) {
        GlobalSetting clientScorePerReservation = globalSettingRepository.findByName("clientScorePerReservation").orElseThrow(NoSuchGlobalSettingException::new);
        GlobalSetting advertiserScorePerReservation = globalSettingRepository.findByName("advertiserScorePerReservation").orElseThrow(NoSuchGlobalSettingException::new);

        clientScorePerReservation.setValue(dto.getClientScorePerReservation().toString());
        advertiserScorePerReservation.setValue(dto.getAdvertiserScorePerReservation().toString());

        globalSettingRepository.save(clientScorePerReservation);
        globalSettingRepository.save(advertiserScorePerReservation);
    }

    public List<LoyaltyProgramCategoryDetailedDisplayDTO> getCategories() {
        return loyaltyProgramCategoryRepository.findAll().stream()
                .map(e -> modelMapper.map(e, LoyaltyProgramCategoryDetailedDisplayDTO.class))
                .sorted(Comparator.comparing(LoyaltyProgramCategoryDetailedDisplayDTO::getPointsLowerBound))
                .collect(Collectors.toList());
    }

    public List<LoyaltyProgramCategoryDetailedDisplayDTO> getCategories(String type) {
        if (type == null)
            return getCategories();

        try {
            TargetedAccountType targetedAccountType = TargetedAccountType.valueOf(type.toUpperCase());
            return loyaltyProgramCategoryRepository.findByTargetedAccountType(targetedAccountType).stream()
                    .map(e -> modelMapper.map(e, LoyaltyProgramCategoryDetailedDisplayDTO.class))
                    .sorted(Comparator.comparing(LoyaltyProgramCategoryDetailedDisplayDTO::getPointsLowerBound))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
    }

    public void updateCategories(LoyaltyProgramCategoriesUpdationDTO dto) {
        List<LoyaltyProgramCategory> categories = new LinkedList<>();
        List<LoyaltyProgramCategory> deletedCategories = new LinkedList<>();

        for (LoyaltyProgramCategoryUpdationDTO categoryDTO : dto.getCategories()) {
            if (categoryDTO.getId() == null && !existsWithTitleAndType(categoryDTO.getTitle(), categoryDTO.getTargetedAccountType())) {
                categories.add(modelMapper.map(categoryDTO, LoyaltyProgramCategory.class));
            } else {
                LoyaltyProgramCategory category = loyaltyProgramCategoryRepository.findById(categoryDTO.getId()).orElseThrow();
                modelMapper.map(categoryDTO, category);
                categories.add(category);
            }
        }

        for (Long id : dto.getDelete()) {
            Optional<LoyaltyProgramCategory> delete = loyaltyProgramCategoryRepository.findById(id);
            delete.ifPresent(deletedCategories::add);
        }

        loyaltyProgramCategoryRepository.saveAll(categories);
        loyaltyProgramCategoryRepository.deleteAll(deletedCategories);
    }

    private void adjustLowestAndHighestBound(List<LoyaltyProgramCategory> categories) {
        List<LoyaltyProgramCategory> advertiserCategories = categories.stream()
                .filter(e -> e.getTargetedAccountType().equals(TargetedAccountType.ADVERTISER)).toList();
        List<LoyaltyProgramCategory> customerCategories = categories.stream()
                .filter(e -> e.getTargetedAccountType().equals(TargetedAccountType.CUSTOMER)).toList();

        Collections.min(advertiserCategories, Comparator.comparing(LoyaltyProgramCategory::getPointsLowerBound)).setPointsLowerBound(0);
        Collections.max(advertiserCategories, Comparator.comparing(LoyaltyProgramCategory::getPointsUpperBound)).setPointsUpperBound(Integer.MAX_VALUE);

        Collections.min(customerCategories, Comparator.comparing(LoyaltyProgramCategory::getPointsLowerBound)).setPointsLowerBound(0);
        Collections.max(customerCategories, Comparator.comparing(LoyaltyProgramCategory::getPointsUpperBound)).setPointsUpperBound(Integer.MAX_VALUE);
    }

    private boolean existsWithTitleAndType(String title, String accountType) {
        return loyaltyProgramCategoryRepository.existsByTitleAndTargetedAccountType(
                title,
                TargetedAccountType.valueOf(accountType));
    }
}
