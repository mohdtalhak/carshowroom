package com.actifyzone.carshowroom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String modelName;

    public int quantity;

    public int assignedQuantity = 0;


    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonIgnore
    public Car car;
}