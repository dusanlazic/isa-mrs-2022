package com.team4.isamrs.model.boat;

import com.team4.isamrs.model.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class BoatReservation extends Reservation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_ad_id")
    private BoatAd advertisement;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}
