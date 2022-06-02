package com.team4.isamrs.service;

import com.team4.isamrs.dto.updation.CommissionRateUpdationDTO;
import com.team4.isamrs.exception.NoSuchGlobalSettingException;
import com.team4.isamrs.model.config.GlobalSetting;
import com.team4.isamrs.repository.GlobalSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalSettingsService {

    @Autowired
    private GlobalSettingRepository globalSettingRepository;

    public void init() {
        globalSettingRepository.save(new GlobalSetting("commissionRate", "0.05"));
    }

    public GlobalSetting getComissionRate() {
        return globalSettingRepository.findByName("commissionRate").orElseThrow(NoSuchGlobalSettingException::new);
    }

    public void updateCommissionRate(CommissionRateUpdationDTO dto) {
        GlobalSetting setting = globalSettingRepository.findByName("commissionRate").orElseThrow(NoSuchGlobalSettingException::new);
        setting.setValue(dto.getValue().toString());
        globalSettingRepository.save(setting);
    }
}
