package com.actifyzone.carshowroom.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String username;

    public String password;

    private String email;

    public String role;

    public String token;

    public LocalDateTime tokenCreatedAt;
    
}