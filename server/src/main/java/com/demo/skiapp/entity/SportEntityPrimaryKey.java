package com.demo.skiapp.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SportEntityPrimaryKey implements Serializable {

    private String sportName;

    private PlaceEntity parentPlace;
}
