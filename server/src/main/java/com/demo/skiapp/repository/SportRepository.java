package com.demo.skiapp.repository;

import com.demo.skiapp.entity.SportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SportRepository extends JpaRepository<SportEntity, String> {

    @Query("SELECT sport FROM SportEntity sport WHERE sport.sportName in (:sportNames) AND sport.endDate <= :endDate AND sport.startDate >= :starDate")
    List<SportEntity> findInSearch(@Param("sportNames")List<String> sportNames,
                                   @Param("startDate")LocalDate startDate,
                                   @Param("endDate")LocalDate endDate);
}
