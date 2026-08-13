package com.actifyzone.carshowroom.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.actifyzone.carshowroom.entity.Customer;
import com.actifyzone.carshowroom.entity.Model;
import com.actifyzone.carshowroom.entity.User;
import com.actifyzone.carshowroom.repository.CarRepository;
import com.actifyzone.carshowroom.repository.CustomerRepository;
import com.actifyzone.carshowroom.repository.ModelRepository;
import com.actifyzone.carshowroom.repository.UserRepository;
import com.actifyzone.carshowroom.service.EmailService;

import org.springframework.web.context.annotation.RequestScope;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.Optional;
import java.time.Duration;
import java.time.LocalDate;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository repo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    CarRepository carRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    ModelRepository modelRepo;

    @PostMapping("/customer")
    public Object saveCustomer(@RequestBody Customer customer, @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if (u == null) return "Invalid Token! Enter the correct Token.";
        if (u.tokenCreatedAt == null) return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if (hours > 24) return "Token Expired! Please Login Again.";

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER")))
            return "Access Denied! You are not Allowed to save a Customer.";

        return repo.save(customer); 
    }

    @GetMapping("/customer")
    public Object getAllCustomers(@RequestHeader("Authorization") String authHeader, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction){
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        
        if(u == null)   return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";
        
        long hours = Duration.between(u.tokenCreatedAt,LocalDateTime.now()).toHours();
        if(hours > 24) return   "Token Expired! Please Login Again.";
        
        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        Pageable pagable = PageRequest.of(page, size, sort);
        
        return repo.findAll(pagable);
    }

    @GetMapping("/customer/search/name/{name}")
    public Object searchByName(@PathVariable String name, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)  return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/customer/filter/name/{name}")
    public Object filterByName(@PathVariable String name, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)  return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.findByName(name);
    }
    
    @GetMapping("/customer/search/email/{email}")
    public Object searchByEmail(@PathVariable String email, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)  return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.findByEmailContainingIgnoreCase(email);
    }

    @GetMapping("/customer/filter/date/{bookingDate}")
    public Object filterByDate(@PathVariable LocalDate bookingDate, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if (u == null)
            return "Invalid Token! Enter the correct Token.";

        if (u.tokenCreatedAt == null)
            return "Please Login Again";

        long hours = Duration.between(
                u.tokenCreatedAt,
                LocalDateTime.now()).toHours();

        if (hours > 24)
            return "Token Expired! Please Login Again.";

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER"))) {
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.findByBookingDate(bookingDate);
    }

    @GetMapping("/customer/filter/email/{email}")
    public Object filterByEmail(@PathVariable String email, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)  return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.findByEmail(email);
    }

    @GetMapping("/customer/search/id/{customerId}")
    public Object getCustomerById(@PathVariable int customerId, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null){
            return "Invalid Token! Enter the correct Token.";
        }
        if(u.tokenCreatedAt == null)
        {
            return "Please Login Again";
        }
        long hours = Duration.between(u.tokenCreatedAt,LocalDateTime.now()).toHours();
        if(hours > 24){
            return "Token Expired! Please Login Again.";
        }
        else{
            if(u.role.equals("OWNER") || u.role.equals("MANAGER")){
                return repo.findById(customerId).get();
            }
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }
    }

    @GetMapping("/customer/query/name/{name}")
    public Object getCustomerByNameQuery(@PathVariable String name, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null){
            return "Invalid Token! Enter the correct Token.";
        }
        if(u.tokenCreatedAt == null)
        {
            return "Please Login Again";
        }
        long hours = Duration.between(u.tokenCreatedAt,LocalDateTime.now()).toHours();
        if(hours > 24){
            return "Token Expired! Please Login Again.";
        }
        else{
            if(u.role.equals("OWNER") || u.role.equals("MANAGER")){
                return repo.getCustomerByName(name);
            }
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }
    }

    @GetMapping("/customer/query/email")
    public Object getCustomerByEmailQuery(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)  return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.getAllSpecEmailUsers();
    }

    @GetMapping("/customer/query/date/{date}")
    public Object getCustomerByDateQuery(@PathVariable String date, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)    return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.getCustomerByDate(date);
    }

    @Transactional(readOnly = true)
    @GetMapping("/customer/query/join/customercar")
    public Object getCustomerandCar(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)    return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.getCustomersWithCars();
    }
    
    @GetMapping("/customer/query/join/customerspeccar/{company}")
    public Object getCustomerwithspecCar(@PathVariable String company, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)    return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.getCustomersWithSpecCars(company);
    }

    @GetMapping("/customer/query/join/customerspeccarmodel/{model}")
    public Object getCustomerwithspeccarsModels(@PathVariable String model, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)    return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.getCustomersWithSpecCarsModels(model);
    }

    @GetMapping("/customer/query/join/customerleftjoincar")
    public Object getCustomerwithcarsleftjoin(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)    return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.getCustomersCarsLeftJoin();
    }

    @GetMapping("/customer/query/join/customerwithcarcount")
    public Object getCustomerwithcarscount(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)    return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.getCustomersCountHavingCars();
    }

    @GetMapping("/customer/query/join/customerprojectionjoincar")
    public Object getCustomerwithcarsprojectedjoin(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null)    return "Invalid Token! Enter the correct Token.";

        if(u.tokenCreatedAt == null)    return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24)    return "Token Expired! Please Login Again.";

        if(!(u.role.equals("OWNER") || u.role.equals("MANAGER"))){
            return "Access Denied! You are not Allowed to access this Restricted Data.";
        }

        return repo.getCustomersCarsProjectionJoin();
    }

    @PutMapping("customer/{customerId}")
    public Object updateCustomer(@RequestBody Customer customer, @PathVariable int customerId, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if (u == null) return "Invalid Token! Enter the correct Token.";
        if (u.tokenCreatedAt == null) return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if (hours > 24) return "Token Expired! Please Login Again.";

        if (u.role.equals("OWNER") || u.role.equals("MANAGER")) {
            Customer c = repo.findById(customerId).get();
            c.name = customer.name;
            c.email = customer.email;
            c.bookingDate = customer.bookingDate;
            return repo.save(c);
        }
        return "Access Denied! You are not Allowed to update the credentials of any Restricted Data.";
    }

    @GetMapping("/customer/count")
    public Object countCustomer(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null){
            return "Invalid Token! Enter the correct Token.";
        }
        if(u.tokenCreatedAt == null)
        {
            return "Please Login Again";
        }
        long hours = Duration.between(u.tokenCreatedAt,LocalDateTime.now()).toHours();
        if(hours > 24){
            return "Token Expired! Please Login Again.";
        }
        else{
            if(u.role.equals("OWNER") || u.role.equals("MANAGER")){
                return repo.count();
            }
            else{
                return "Access Denied! You are not Allowed to access this Restricted Data.";
            }
        }
    }
    
    @GetMapping("/car/count")
    public Object countCar(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null){
            return "Invalid Token! Enter the correct Token.";
        }
        if(u.tokenCreatedAt == null)
        {
            return "Please Login Again";
        }
        long hours = Duration.between(u.tokenCreatedAt,LocalDateTime.now()).toHours();
        if(hours > 24){
            return "Token Expired! Please Login Again.";
        }
        else{
            if(u.role.equals("OWNER") || u.role.equals("MANAGER")){
                return modelRepo.getTotalQuantity();
            }
            else{
                return "Access Denied! You are not Allowed to access this Restricted Data.";
            }
        }
    }

    @DeleteMapping("/customer/{customerId}")
    public String deleteCustomer(@PathVariable int customerId, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null){
            return "Invalid Token! Enter the correct Token.";
        }
        if(u.tokenCreatedAt == null)
        {
            return "Please Login Again";
        }
        long hours = Duration.between(u.tokenCreatedAt,LocalDateTime.now()).toHours();
        if(hours > 24){
            return "Token Expired! Please Login Again.";
        }
        else{
            if(u.role.equals("OWNER"))
            {
                repo.deleteById(customerId);
                return "Customer Deleted";
            }
            return "Access Denied! You are not allowed to Delete a Customer.";
        }
    }

    @GetMapping("/dashboard")
    public String getDashboard(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null){
            return "Invalid Token! Enter the correct TOken.";
        }
        if(u.tokenCreatedAt == null){
            return "Please Login Again!";
        }
        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if(hours > 24){
            return "Token Expired! Please Login Again!";
        }
        if(u.role.equals("OWNER")){
            String dashboardData = "Total Number Of Customers : " + repo.count() + 
            "\n" + "Total Number Of Cars : " + carRepo.count() +
            "\n" + "Total Number Of Users : " + userRepo.count();
            return dashboardData;
            
        }
        return "Access Denied! You are not allowed to Access the DashBoard.";
    }

    @DeleteMapping("/user/{userId}")
    public String deleteUser(@PathVariable int userId, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if(u == null){
            return "Invalid Token! Enter the correct Token.";
        }
        if(u.tokenCreatedAt == null)
        {
            return "Please Login Again";
        }
        long hours = Duration.between(u.tokenCreatedAt,LocalDateTime.now()).toHours();
        if(hours > 24){
            return "Token Expired! Please Login Again.";
        }
        else{
            if(u.role.equals("OWNER"))
            {
                userRepo.deleteById(userId);
                return  "Deleted";
            }
            return "Access Denied! You are not allowed to Delete a Customer.";
        }
    }

    @PostMapping("/customer/{customerId}/assign")
    public Object assignModels(
            @PathVariable int customerId,
            @RequestBody List<Integer> modelIds,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        User u = userRepo.findByToken(token);
        if (u == null) return "Invalid Token! Enter the correct Token.";
        if (u.tokenCreatedAt == null) return "Please Login Again";

        long hours = Duration.between(u.tokenCreatedAt, LocalDateTime.now()).toHours();
        if (hours > 24) return "Token Expired! Please Login Again.";

        if (!(u.role.equals("OWNER") || u.role.equals("MANAGER")))
            return "Access Denied! You are not Allowed to assign Cars.";

        Optional<Customer> customerOpt = repo.findById(customerId);
        if (customerOpt.isEmpty()) return "Customer Not Found.";
        Customer customer = customerOpt.get();

        List<String> assigned = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        for (int modelId : modelIds) {

            Optional<Model> modelOpt = modelRepo.findById(modelId);

            if (modelOpt.isEmpty()) {
                failed.add("Model ID " + modelId + " not found.");
                continue;
            }

            Model model = modelOpt.get();
            int available = model.quantity - model.assignedQuantity;

            if (available <= 0) {
                failed.add(model.modelName + " (ID " + modelId + ") already fully assigned, cannot assign again.");
                continue;
            }

            model.assignedQuantity += 1;
            modelRepo.save(model);

            customer.assignedModels.add(model);
            assigned.add(model.modelName + " (ID " + modelId + ")");
        }

        repo.save(customer);

        if (assigned.isEmpty()) {
            return "All entered Car Models already Assigned. Cannot Assign again.";
        }

        if ("Interested".equalsIgnoreCase(customer.getMarketing())) {
            try {
                emailService.sendBookingMail(customer);
            } catch (Exception e) {
                System.out.println("Email failed to send: " + e.getMessage());
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("assigned", assigned);
        result.put("failed", failed);
        result.put("customer", customer);
        return result;
    }
}