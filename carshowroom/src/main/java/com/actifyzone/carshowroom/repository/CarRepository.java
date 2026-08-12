package com.actifyzone.carshowroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.actifyzone.carshowroom.entity.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

    Optional<Car> findByCompanyIgnoreCase(String company);

}