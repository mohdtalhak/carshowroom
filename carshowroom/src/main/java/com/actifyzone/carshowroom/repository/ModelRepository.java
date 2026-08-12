package com.actifyzone.carshowroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.actifyzone.carshowroom.entity.Model;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    List<Model> findByCarId(int carId);

    List<Model> findByModelNameContainingIgnoreCase(String modelName);

    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM Model m")
    Integer getTotalQuantity();

}