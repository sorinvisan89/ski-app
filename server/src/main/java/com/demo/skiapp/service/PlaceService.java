package com.demo.skiapp.service;

import com.demo.skiapp.entity.PlaceEntity;
import com.demo.skiapp.entity.SportEntity;
import com.demo.skiapp.exception.ServiceException;
import com.demo.skiapp.mapper.CustomDataMapper;
import com.demo.skiapp.model.Place;
import com.demo.skiapp.model.Sport;
import com.demo.skiapp.model.UpdatablePlaceFields;
import com.demo.skiapp.repository.PlaceRepository;
import com.demo.skiapp.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final CustomDataMapper customDataMapper;

    private final ServiceUtils serviceUtils;

    @Autowired
    public PlaceService(PlaceRepository placeRepository,
                        CustomDataMapper customDataMapper,
                        ServiceUtils serviceUtils) {
        this.placeRepository = placeRepository;
        this.customDataMapper = customDataMapper;
        this.serviceUtils = serviceUtils;
    }

    public void addNewPlace(Place newPlace) {
        String toAdd = newPlace.getPlaceName();
        Optional<PlaceEntity> existing = placeRepository.findById(toAdd);
        if (existing.isPresent()) {
            throw new ServiceException("Place: " + toAdd + " already exists!", HttpStatus.CONFLICT);
        }
        PlaceEntity toPersist = customDataMapper.toPlaceEntity(newPlace);
        placeRepository.save(toPersist);
    }

    public List<Place> getPlaces() {
        List<PlaceEntity> entites = placeRepository.findAll();
        return entites.stream()
                .map(customDataMapper::toPlaceModel)
                .collect(Collectors.toList());
    }

    public void deletePlace(String placeName) {
        PlaceEntity toDelete = serviceUtils.shouldFindById(placeName, placeRepository::findById);
        placeRepository.delete(toDelete);
    }

    public Place getPlace(String placeName) {
        PlaceEntity existing = serviceUtils.shouldFindById(placeName, placeRepository::findById);
        return customDataMapper.toPlaceModel(existing);
    }

    public void partialUpdate(String placeName, UpdatablePlaceFields updatablePlaceFields) {
        PlaceEntity toUpdate = serviceUtils.shouldFindById(placeName, placeRepository::findById);
        if (updatablePlaceFields.getCountry() != null) {
            toUpdate.setCountry(updatablePlaceFields.getCountry());
        }
        if (updatablePlaceFields.getRegion() != null) {
            toUpdate.setRegion(updatablePlaceFields.getRegion());
        }
        if (updatablePlaceFields.getZone() != null) {
            toUpdate.setZone(updatablePlaceFields.getZone());
        }

        List<Sport> sportsToUpdate = updatablePlaceFields.getSports();
        if (sportsToUpdate != null) {
            Set<SportEntity> newSports = new HashSet<>();
            sportsToUpdate.forEach(sport -> newSports.add(customDataMapper.toSportEntity(sport)));
            toUpdate.setSports(newSports);
        }

        placeRepository.save(toUpdate);
    }


    public void update(String placeName, UpdatablePlaceFields updatablePlaceFields) {
        PlaceEntity toUpdate = serviceUtils.shouldFindById(placeName, placeRepository::findById);

        toUpdate.setCountry(updatablePlaceFields.getCountry());
        toUpdate.setRegion(updatablePlaceFields.getRegion());
        toUpdate.setZone(updatablePlaceFields.getZone());

        List<Sport> sportsToUpdate = updatablePlaceFields.getSports();
        if (sportsToUpdate != null) {
            Set<SportEntity> newSports = new HashSet<>();
            sportsToUpdate.forEach(sport -> newSports.add(customDataMapper.toSportEntity(sport)));
            toUpdate.setSports(newSports);
        } else {
            toUpdate.setSports(null);
        }

        placeRepository.save(toUpdate);
    }

    private void updateExisting(){

    }
}
