package com.demo.skiapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "places")
public class PlaceEntity implements Serializable {

    @Id
    @Column(name = "place_name")
    private String placeName;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "parentPlace")
    @JsonBackReference
    List<SportEntity> sports = new ArrayList<>();

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "zone")
    private String zone;

    public void addSport(SportEntity sport) {
        this.sports.add(sport);
        sport.setParentPlace(this);
    }
}
