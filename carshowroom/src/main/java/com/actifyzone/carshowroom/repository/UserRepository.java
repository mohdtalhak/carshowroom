package com.actifyzone.carshowroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.actifyzone.carshowroom.entity.User;

public interface UserRepository
extends JpaRepository<User,Integer>
{
    User findByUsernameAndPassword(String username, String password);

    User findByToken(String token);
    
}