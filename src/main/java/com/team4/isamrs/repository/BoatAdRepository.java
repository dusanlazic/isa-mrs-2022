package com.team4.isamrs.repository;

import com.team4.isamrs.model.boat.BoatAd;
import com.team4.isamrs.model.resort.ResortAd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatAdRepository extends JpaRepository<BoatAd, Long> {
}
