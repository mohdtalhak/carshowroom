package com.actifyzone.carshowroom.entity;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "customer_models",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    public List<Model> assignedModels = new ArrayList<>();
}