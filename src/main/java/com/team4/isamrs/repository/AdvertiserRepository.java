package com.team4.isamrs.repository;

import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> {
}