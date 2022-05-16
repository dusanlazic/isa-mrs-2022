package com.team4.isamrs.repository;

import com.team4.isamrs.model.resort.ResortAd;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResortAdRepository  extends JpaRepository<ResortAd, Long> {
    Optional<ResortAd> findResortAdByIdAndAdvertiser(Long id, Advertiser advertiser);

    Optional<ResortAd> findAdventureAdByIdAndAdvertiser(Long id, Advertiser advertiser);
}
