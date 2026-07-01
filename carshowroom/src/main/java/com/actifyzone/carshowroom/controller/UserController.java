package com.actifyzone.carshowroom.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.actifyzone.carshowroom.entity.User;
import com.actifyzone.carshowroom.repository.UserRepository;




@RestController
public class UserController {

    @Autowired
    UserRepository repo;

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return repo.save(user);

    }
    
    @PostMapping("/login")
    public String login(@RequestBody User user)
    {
        User u = repo.findByUsernameAndPassword(user.username, user.password);
        
        if(u == null){
            return "Invalid Username and Password!";
        }
        if(u.token != null){
            return "Already Logged In, Cannot re-Login again!";
        }
            String token = UUID.randomUUID().toString();
            u.token = token;
            u.tokenCreatedAt = LocalDateTime.now();
            repo.save(u);
            return "You have LoggedIn Successfully!\nToken : " + token;
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = repo.findByToken(token);
        if(u == null){
            return "Invalid Token";
        }
        u.token = null;
        u.tokenCreatedAt = null;
        repo.save(u);
        return "You have LoggedOut Successfully, and your Token has been Expired! ";
    }

    @GetMapping("/profile")
    public Object profile(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = repo.findByToken(token);
        if(u.role.equals("CUSTOMER"))
        {
            return u;
        }
        return "Only the Customer Himself Can View his own Profile!";
    }
    
}