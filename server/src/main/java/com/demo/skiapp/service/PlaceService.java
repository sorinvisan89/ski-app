package com.demo.skiapp.service;

import com.demo.skiapp.entity.PlaceEntity;
import com.demo.skiapp.entity.SportEntity;
import com.demo.skiapp.entity.specification.PlaceSpecification;
import com.demo.skiapp.exception.ServiceException;
import com.demo.skiapp.mapper.CustomDataMapper;
import com.demo.skiapp.model.Place;
import com.demo.skiapp.model.Sport;
import com.demo.skiapp.model.UpdatablePlaceFields;
import com.demo.skiapp.repository.PlaceRepository;
import com.demo.skiapp.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final CustomDataMapper customDataMapper;

    private final ServiceUtils serviceUtils;

    @Autowired
    public PlaceService(
            PlaceRepository placeRepository,
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
        List<SportEntity> sports = newPlace.getSports().stream()
                .map(customDataMapper::toSportEntity)
                .collect(Collectors.toList());

        sports.forEach(toPersist::addSport);
        placeRepository.save(toPersist);
    }

    public List<Place> getPlaces(
            String freeTextSearchPattern,
            Integer page,
            Integer limit) {

        if (StringUtils.isEmpty(freeTextSearchPattern)) {
            Page<PlaceEntity> entites = placeRepository.findAll(PageRequest.of(page, limit));
            return mapPageToModel(entites);
        } else {
            Page<PlaceEntity> entites = placeRepository.findAll(
                    new PlaceSpecification(freeTextSearchPattern),
                    PageRequest.of(page, limit));

            return mapPageToModel(entites);
        }
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
            toUpdate.getSports().clear();
            sportsToUpdate.forEach(sport -> {
                SportEntity newSport = customDataMapper.toSportEntity(sport);
                toUpdate.addSport(newSport);
            });
        }
    }

    public void update(String placeName, UpdatablePlaceFields updatablePlaceFields) {
        PlaceEntity toUpdate = serviceUtils.shouldFindById(placeName, placeRepository::findById);

        toUpdate.setCountry(updatablePlaceFields.getCountry());
        toUpdate.setRegion(updatablePlaceFields.getRegion());
        toUpdate.setZone(updatablePlaceFields.getZone());

        List<Sport> sportsToUpdate = updatablePlaceFields.getSports();
        if (sportsToUpdate != null) {
            toUpdate.getSports().clear();
            sportsToUpdate.forEach(sport -> {
                SportEntity newSport = customDataMapper.toSportEntity(sport);
                toUpdate.addSport(newSport);
            });
        } else {
            toUpdate.setSports(null);
        }
    }

    private List<Place> mapPageToModel(Page<PlaceEntity> entites) {
        return entites.getContent().stream()
                .map(customDataMapper::toPlaceModel)
                .collect(Collectors.toList());
    }
}
