package com.actifyzone.carshowroom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.actifyzone.carshowroom.entity.Customer;

import com.actifyzone.carshowroom.repository.CustomerRepository;

@Component
public class EmailScheduler {

    @Autowired
    EmailService emailService;

    @Autowired
    CustomerRepository customerRepo;

    @Scheduled(cron = "0 21 10 * * *")
    public void dailyMarketingMail() {

        List<Customer> customers = customerRepo.findAll();

        for (Customer c : customers) {

            if (c.getMarketing().equalsIgnoreCase("Interested")) {
                emailService.sendMarketingMail(c);
            }
        }
    }
    
}