package com.actifyzone.carshowroom.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import com.actifyzone.carshowroom.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByName(String name);

    Page<Customer> findAll(Pageable pageable);

    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByEmailContainingIgnoreCase(String name);

    List<Customer> findByBookingDate(LocalDateTime bookingDate);

    List<Customer> findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.name = :name")
    List<Customer> getCustomerByName(@Param("name") String name);

    @Query("SELECT c FROM Customer c WHERE c.email LIKE '%gmail.com'")
    List<Customer> getAllSpecEmailUsers();

    @Query("SELECT c FROM Customer c WHERE c.bookingDate = :date")
    List<Customer> getCustomerByDate(@Param("date") String date);

    @Query("SELECT c FROM Customer c JOIN c.assignedModels m")
    List<Customer> getCustomersWithCars();

    @Query("SELECT c FROM Customer c JOIN c.assignedModels m WHERE m.car.company = :company")
    List<Customer> getCustomersWithSpecCars(@Param("company") String company);

    @Query("SELECT c FROM Customer c JOIN c.assignedModels m WHERE m.modelName = :model")
    List<Customer> getCustomersWithSpecCarsModels(@Param("model") String model);

    @Query("SELECT c FROM Customer c LEFT JOIN c.assignedModels m")
    List<Customer> getCustomersCarsLeftJoin();

    @Query("SELECT COUNT(c) FROM Customer c JOIN c.assignedModels m")
    Long getCustomersCountHavingCars();

    @Query("SELECT c.name, c.email, m.modelName FROM Customer c JOIN c.assignedModels m")
    List<Object[]> getCustomersCarsProjectionJoin();

    Object findByBookingDate(LocalDate bookingDate);
}