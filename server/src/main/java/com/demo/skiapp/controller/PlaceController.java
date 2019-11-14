package com.demo.skiapp.controller;

import com.demo.skiapp.api.PlacesApiDelegate;
import com.demo.skiapp.config.aop.LogExecution;
import com.demo.skiapp.model.Place;
import com.demo.skiapp.model.UpdatablePlaceFields;
import com.demo.skiapp.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@LogExecution
public class PlaceController implements PlacesApiDelegate {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Override
    public ResponseEntity<Void> addPlace(Place place) {
        placeService.addNewPlace(place);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deletePlace(String placeName) {
        placeService.deletePlace(placeName);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Place> getPlace(String placeName) {
        Place result = placeService.getPlace(placeName);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<Place>> getPlaces(String freeTextSearchPattern,
                                                 Integer page,
                                                 Integer limit) {
        List<Place> results = placeService.getPlaces(freeTextSearchPattern, page, limit);
        return !results.isEmpty() ? ResponseEntity.ok(results) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> partialUpdatePlace(String placeName, UpdatablePlaceFields updatablePlaceFields) {
        placeService.partialUpdate(placeName, updatablePlaceFields);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updatePlace(String placeName, UpdatablePlaceFields updatablePlaceFields) {
        placeService.update(placeName, updatablePlaceFields);
        return ResponseEntity.ok().build();
    }
}
