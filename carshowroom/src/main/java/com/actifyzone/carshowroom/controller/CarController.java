package com.actifyzone.carshowroom.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.actifyzone.carshowroom.entity.Car;
import com.actifyzone.carshowroom.entity.Model;
import com.actifyzone.carshowroom.entity.User;
import com.actifyzone.carshowroom.repository.CarRepository;
import com.actifyzone.carshowroom.repository.ModelRepository;
import com.actifyzone.carshowroom.repository.UserRepository;

@RestController
public class CarController {

    @Autowired
    CarRepository carRepo;

    @Autowired
    ModelRepository modelRepo;

    @Autowired
    UserRepository userRepo;




    @PostMapping("/car")    // ADD COMPANY WITH MODELS
    public Object addCar(@RequestBody Car car,
                         @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        User u = userRepo.findByToken(token);

        if (u == null) {
            return "Invalid Token! Enter the correct Token.";
        }

        if (u.tokenCreatedAt == null) {
            return "Please Login Again";
        }

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()
        ).toHours();

        if (hours > 24) {
            return "Token Expired! Please Login Again.";
        }

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER"))) {
            return "Access Denied! You are not Allowed to add Cars.";
        }


        // Check whether company already exists
        Optional<Car> existingCar =
                carRepo.findByCompanyIgnoreCase(car.company);

        if (existingCar.isPresent()) {

            Car existing = existingCar.get();

            if (car.models != null) {

                for (Model model : car.models) {

                    model.car = existing;

                    if (model.assignedQuantity < 0) {
                        model.assignedQuantity = 0;
                    }

                    existing.models.add(model);
                }
            }

            return carRepo.save(existing);
        }


        // New company
        if (car.models != null) {

            for (Model model : car.models) {

                model.car = car;

                if (model.assignedQuantity < 0) {
                    model.assignedQuantity = 0;
                }
            }
        }

        return carRepo.save(car);
    }




    @GetMapping("/car")    // GET ALL COMPANIES WITH MODELS
    public Object getAllCars(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        User u = userRepo.findByToken(token);

        if (u == null) {
            return "Invalid Token! Enter the correct Token.";
        }

        if (u.tokenCreatedAt == null) {
            return "Please Login Again";
        }

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()
        ).toHours();

        if (hours > 24) {
            return "Token Expired! Please Login Again.";
        }

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER"))) {
            return "Access Denied! You are not Allowed to access Cars.";
        }

        return carRepo.findAll();
    }



    @GetMapping("/car/{carId}")// GET COMPANY BY ID
    public Object getCarById(
            @PathVariable int carId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        User u = userRepo.findByToken(token);

        if (u == null) {
            return "Invalid Token! Enter the correct Token.";
        }

        if (u.tokenCreatedAt == null) {
            return "Please Login Again";
        }

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()
        ).toHours();

        if (hours > 24) {
            return "Token Expired! Please Login Again.";
        }

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER"))) {
            return "Access Denied! You are not Allowed to access Cars.";
        }

        Optional<Car> car = carRepo.findById(carId);

        if (car.isEmpty()) {
            return "Car Company Not Found.";
        }

        return car.get();
    }




    @GetMapping("/car/{carId}/models")    // GET MODELS OF A COMPANY
    public Object getModelsByCar(
            @PathVariable int carId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        User u = userRepo.findByToken(token);

        if (u == null) {
            return "Invalid Token! Enter the correct Token.";
        }

        if (u.tokenCreatedAt == null) {
            return "Please Login Again";
        }

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()
        ).toHours();

        if (hours > 24) {
            return "Token Expired! Please Login Again.";
        }

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER"))) {
            return "Access Denied! You are not Allowed to access Models.";
        }

        Optional<Car> car = carRepo.findById(carId);

        if (car.isEmpty()) {
            return "Car Company Not Found.";
        }

        return modelRepo.findByCarId(carId);
    }




    @GetMapping("/model/search/{modelName}")    // SEARCH MODEL
    public Object searchModel(
            @PathVariable String modelName,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        User u = userRepo.findByToken(token);

        if (u == null) {
            return "Invalid Token! Enter the correct Token.";
        }

        if (u.tokenCreatedAt == null) {
            return "Please Login Again";
        }

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()
        ).toHours();

        if (hours > 24) {
            return "Token Expired! Please Login Again.";
        }

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER"))) {
            return "Access Denied!";
        }

        return modelRepo.findByModelNameContainingIgnoreCase(modelName);
    }




    @GetMapping("/model/{modelId}")    // GET MODEL BY ID
    public Object getModelById(
            @PathVariable int modelId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        User u = userRepo.findByToken(token);

        if (u == null) {
            return "Invalid Token! Enter the correct Token.";
        }

        if (u.tokenCreatedAt == null) {
            return "Please Login Again";
        }

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()
        ).toHours();

        if (hours > 24) {
            return "Token Expired! Please Login Again.";
        }

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER"))) {
            return "Access Denied!";
        }

        Optional<Model> model = modelRepo.findById(modelId);

        if (model.isEmpty()) {
            return "Car Model Not Found.";
        }

        return model.get();
    }




    @GetMapping("/model/{modelId}/availability")    // CHECK MODEL AVAILABILITY
    public Object checkAvailability(
            @PathVariable int modelId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        User u = userRepo.findByToken(token);

        if (u == null) {
            return "Invalid Token! Enter the correct Token.";
        }

        if (u.tokenCreatedAt == null) {
            return "Please Login Again";
        }

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()
        ).toHours();

        if (hours > 24) {
            return "Token Expired! Please Login Again.";
        }

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER"))) {
            return "Access Denied!";
        }

        Optional<Model> modelOptional = modelRepo.findById(modelId);

        if (modelOptional.isEmpty()) {
            return "Car Model Not Found.";
        }

        Model model = modelOptional.get();

        int availableQuantity =
                model.quantity - model.assignedQuantity;

        if (availableQuantity <= 0) {
            return "Car Model Not Available.";
        }

        return "Available Quantity of "
                + model.modelName
                + " : "
                + availableQuantity;
    }




    @PutMapping("/model/{modelId}/storage")    // ADD MORE STOCK TO EXISTING MODEL
    public Object addStorage(
            @PathVariable int modelId,
            @RequestParam int quantity,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        User u = userRepo.findByToken(token);

        if (u == null) {
            return "Invalid Token! Enter the correct Token.";
        }

        if (u.tokenCreatedAt == null) {
            return "Please Login Again";
        }

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()
        ).toHours();

        if (hours > 24) {
            return "Token Expired! Please Login Again.";
        }

        if (!u.role.equals("OWNER")) {
            return "Access Denied! Only OWNER can add Storage.";
        }

        if (quantity <= 0) {
            return "Storage Quantity must be greater than 0.";
        }

        Optional<Model> modelOptional = modelRepo.findById(modelId);

        if (modelOptional.isEmpty()) {
            return "Car Model Not Found.";
        }

        Model model = modelOptional.get();

        model.quantity += quantity;

        return modelRepo.save(model);
    }
}