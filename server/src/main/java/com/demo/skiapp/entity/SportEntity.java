package com.demo.skiapp.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "sports")
@EqualsAndHashCode(exclude = "parentPlace")
@ToString(exclude = "parentPlace")
public class SportEntity implements Serializable {

    @Id
    @Column(name = "sport_name")
    private String sportName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "daily_average_cost")
    private BigDecimal dailyAverageCost;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "place_sport_name",
            foreignKey = @ForeignKey(name = "places_fk")
    )
    @JsonManagedReference
    private PlaceEntity parentPlace;

}
