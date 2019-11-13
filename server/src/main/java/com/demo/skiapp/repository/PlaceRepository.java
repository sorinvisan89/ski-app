package com.demo.skiapp.repository;


import com.demo.skiapp.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<PlaceEntity, String> {

}
