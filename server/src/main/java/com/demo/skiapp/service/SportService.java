package com.demo.skiapp.service;

import com.demo.skiapp.entity.SportEntity;
import com.demo.skiapp.mapper.CustomDataMapper;
import com.demo.skiapp.model.Place;
import com.demo.skiapp.model.PlaceSearchResult;
import com.demo.skiapp.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class SportService {

    private final SportRepository sportRepository;

    private final CustomDataMapper customDataMapper;

    @Autowired
    public SportService(SportRepository sportRepository, CustomDataMapper customDataMapper) {
        this.sportRepository = sportRepository;
        this.customDataMapper = customDataMapper;
    }

    public List<PlaceSearchResult> searchPlaces(List<String> sports, LocalDate startDate, LocalDate endDate) {
        long daysBetween = DAYS.between(startDate, endDate);
        //TODO
        List<SportEntity> results = sportRepository.findInSearch(sports, startDate, endDate);
        return null;
    }

}
