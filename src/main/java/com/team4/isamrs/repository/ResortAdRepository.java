package com.team4.isamrs.repository;

import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.advertisement.ResortAd;
import com.team4.isamrs.model.user.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface ResortAdRepository  extends JpaRepository<ResortAd, Long> {
    Optional<ResortAd> findResortAdByIdAndAdvertiser(Long id, Advertiser advertiser);

    @Query("SELECT rad FROM ResortAd rad JOIN Address a ON rad.address = a.id WHERE" +
            "(CAST(:guests AS string) IS NULL OR rad.capacity >= :guests) AND " +
            "(CAST(:where AS string) IS NULL " +
            "OR LOWER(a.city) LIKE LOWER(concat('%', :where, '%'))" +
            "OR LOWER(a.state) LIKE LOWER(concat('%', :where, '%')))")
    List<ResortAd> search(
            @Param("where") String where,
            @Param("guests") int guests);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT r FROM ResortAd r WHERE r.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<ResortAd> lockGetById(Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT r FROM ResortAd r WHERE r.id = ?1 AND r.advertiser = ?2")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<ResortAd> lockFindResortAdByIdAndAdvertiser(Long id, Advertiser advertiser);
}
