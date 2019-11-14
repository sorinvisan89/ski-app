package com.demo.skiapp.mapper;

import com.demo.skiapp.entity.PlaceEntity;
import com.demo.skiapp.entity.SportEntity;
import com.demo.skiapp.model.Place;
import com.demo.skiapp.model.Sport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomDataMapper {

    @Mappings(
            @Mapping(target = "sports", ignore = true)
    )
    PlaceEntity toPlaceEntity(Place model);

    Place toPlaceModel(PlaceEntity entity);

    SportEntity toSportEntity(Sport model);

    Sport toSportModel(SportEntity entity);
}
