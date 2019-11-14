package com.demo.skiapp.service;

import com.demo.skiapp.entity.SportEntity;
import com.demo.skiapp.model.PlaceSearchResult;
import com.demo.skiapp.model.SportSearchResult;
import com.demo.skiapp.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class SearchService {

    private final SportRepository sportRepository;

    @Autowired
    public SearchService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public List<PlaceSearchResult> searchPlaces(List<String> sports, LocalDate startDate, LocalDate endDate) {
        long daysBetween = DAYS.between(startDate, endDate);
        List<SportEntity> entities = sportRepository.findInSearch(sports, startDate, endDate);

        Map<String, List<SportEntity>> groupByPlace = entities.stream()
                .collect(Collectors.groupingBy(sport -> sport.getParentPlace().getPlaceName()));

        return groupByPlace.entrySet()
                .stream()
                .map(entry -> {
                    String sportName = entry.getKey();
                    List<SportEntity> sportEntities = entry.getValue();
                    PlaceSearchResult result = new PlaceSearchResult();
                    result.setPlaceName(sportName);
                    result.setDays(daysBetween);
                    List<SportSearchResult> sportResults = sportEntities.stream()
                            .map(foundSport -> new SportSearchResult()
                                    .costPerSport(BigDecimal.valueOf(daysBetween).multiply(foundSport.getDailyAverageCost()))
                                    .averageCostPerDay(foundSport.getDailyAverageCost())
                                    .sportName(foundSport.getSportName()))
                            .collect(Collectors.toList());
                    result.setSports(sportResults);
                    BigDecimal totalCost = sportResults.stream()
                            .map(SportSearchResult::getCostPerSport)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    result.setTotalCost(totalCost);
                    return result;
                })
                .sorted(Comparator.comparing(PlaceSearchResult::getTotalCost)
                )
                .collect(Collectors.toList());
    }

}
