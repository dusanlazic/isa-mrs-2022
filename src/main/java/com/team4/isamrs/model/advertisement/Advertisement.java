package com.team4.isamrs.model.advertisement;

import com.team4.isamrs.exception.PhotoNotFoundException;
import com.team4.isamrs.model.reservation.Reservation;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.model.user.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Advertisement {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advertiser_id")
    private Advertiser advertiser;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "pricing_description")
    private String pricingDescription;

    @Column(name = "available_after")
    private LocalDate availableAfter;

    @Column(name = "available_until")
    private LocalDate availableUntil;

    @Column(name = "rules", nullable = false)
    private String rules;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "check_out_time", nullable = false)
    private LocalTime checkOutTime; // e.g. 10:00

    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime; // e.g. 13:00

    /*
    e.g. WIFI, Pet friendly, TV
     */
    @ManyToMany(mappedBy = "advertisements", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @OrderColumn
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderColumn
    private List<Option> options = new ArrayList<>();

    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.LAZY)
    private Set<Customer> subscribers = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getAdvertisements().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getAdvertisements().remove(this);
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

    public void addOption(Option option) {
        options.add(option);
        option.setAdvertisement(this);
    }

    public void removeOption(Option option) {
        options.remove(option);
        option.setAdvertisement(null);
    }

    public void verifyPhotosOwnership(Advertiser advertiser) {
        String username = advertiser.getUsername();
        photos.forEach(photo -> {
            if (!photo.getUploader().getUsername().equals(username))
                 throw new PhotoNotFoundException();
        });
    }
}
