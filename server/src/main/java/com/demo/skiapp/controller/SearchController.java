package com.demo.skiapp.controller;

import com.demo.skiapp.api.SearchApiDelegate;
import com.demo.skiapp.config.aop.LogExecution;
import com.demo.skiapp.model.PlaceSearchResult;
import com.demo.skiapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
@LogExecution
public class SearchController implements SearchApiDelegate {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public ResponseEntity<List<PlaceSearchResult>> searchPlaces(List<String> sports, LocalDate startDate, LocalDate endDate) {
        List<PlaceSearchResult> results = searchService.searchPlaces(sports, startDate, endDate);
        return !results.isEmpty() ? ResponseEntity.ok(results) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
