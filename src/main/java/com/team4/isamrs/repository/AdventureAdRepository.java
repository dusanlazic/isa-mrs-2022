package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.AdventureAd;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdventureAdRepository extends JpaRepository<AdventureAd, Long> {

    Optional<AdventureAd> findAdventureAdByIdAndAdvertiser(Long id, Advertiser advertiser);

    @Query("SELECT ad FROM AdventureAd ad JOIN Address a ON ad.address = a.id WHERE" +
            "(CAST(:guests AS string) IS NULL OR ad.capacity >= :guests) AND " +
            "(CAST(:where AS string) IS NULL " +
            "OR LOWER(a.city) LIKE LOWER(concat('%', :where, '%'))" +
            "OR LOWER(a.state) LIKE LOWER(concat('%', :where, '%')))")
    List<AdventureAd> search(
            @Param("where") String where,
            @Param("guests") int guests);
}
