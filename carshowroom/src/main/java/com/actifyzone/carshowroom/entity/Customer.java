package com.actifyzone.carshowroom.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String name;

    public String email;

    public LocalDate bookingDate;

    private String marketing;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Car> cars;
    
}