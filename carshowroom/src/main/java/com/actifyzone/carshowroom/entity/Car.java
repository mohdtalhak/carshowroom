package com.actifyzone.carshowroom.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String company;

    @OneToMany(mappedBy = "car",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    public List<Model> models = new ArrayList<>();
}