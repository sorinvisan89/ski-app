package com.demo.skiapp.repository;


import com.demo.skiapp.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlaceRepository extends JpaRepository<PlaceEntity, String>, JpaSpecificationExecutor<PlaceEntity> {

}
