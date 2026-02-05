package com.solinone.todoc.place.domain;

import com.solinone.todoc.global.common.BaseEntity;
import com.solinone.todoc.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "places",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"business_number", "address"})
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String zoneCode;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private LocalDate openedAt;

    @Builder
    public Place(
            User user,
            String placeName,
            PlaceType placeType,
            Double latitude,
            Double longitude,
            String address,
            String zoneCode,
            String businessNumber,
            LocalDate openedAt) {
        this.user = user;
        this.placeName = placeName;
        this.placeType = placeType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.zoneCode = zoneCode;
        this.businessNumber = businessNumber;
        this.openedAt = openedAt;
    }
}
