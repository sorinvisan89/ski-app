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
@ToString(exclude = "parentPlace")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SportEntity implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "sport_name")
    private String sportName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "daily_average_cost")
    private BigDecimal dailyAverageCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_parent")
    @JsonManagedReference
    private PlaceEntity parentPlace;
}
