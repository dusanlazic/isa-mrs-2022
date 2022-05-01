package com.team4.isamrs.repository;

import com.team4.isamrs.model.adventure.AdventureAd;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdventureAdRepository extends JpaRepository<AdventureAd, Long> {

    Optional<AdventureAd> findAdventureAdByIdAndAdvertiser(Long id, Advertiser advertiser);
}
