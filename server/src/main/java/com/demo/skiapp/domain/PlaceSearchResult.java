package com.demo.skiapp.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceSearchResult {

    private String name;

    private List<String> sports;
}
