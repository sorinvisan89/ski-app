package com.demo.skiapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "places")
public class PlaceEntity implements Serializable {

    @Id
    @Column(name = "place_name")
    private String placeName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parentPlace")
    @JsonBackReference
    Set<SportEntity> sports = new HashSet<>();

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "zone")
    private String zone;

    public void removeSport(SportEntity sport) {
        this.sports.remove(sport);
    }

    public void addSport(SportEntity sport) {
        this.sports.add(sport);
    }

    public void setSports(Set<SportEntity> newSports) {
        this.sports.clear();
        if (newSports != null) {
            this.sports.addAll(newSports);
        }
    }

}
