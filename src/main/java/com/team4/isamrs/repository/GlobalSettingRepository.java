package com.team4.isamrs.repository;

import com.team4.isamrs.model.config.GlobalSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GlobalSettingRepository extends JpaRepository<GlobalSetting, String> {
    Optional<GlobalSetting> findByName(String name);
}
