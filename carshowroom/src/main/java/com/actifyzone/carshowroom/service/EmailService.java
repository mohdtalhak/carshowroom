// package com.actifyzone.carshowroom.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// import com.actifyzone.carshowroom.entity.Car;
// import com.actifyzone.carshowroom.entity.Customer;
// import com.actifyzone.carshowroom.entity.User;
// import com.actifyzone.carshowroom.repository.UserRepository;

// @Service
// public class EmailService {

//     @Autowired
//     JavaMailSender mailSender;

//     public void sendBookingMail(Customer customer) {

//         SimpleMailMessage message = new SimpleMailMessage();

//         message.setTo(customer.getEmail());

//         message.setSubject("🎉 Congratulations on Your New Car Purchase!");

//         String text = "Dear " + customer.getName() + "\nWarm Greetings from Car Showroom!\n" + //
//                 "\n" + //
//                 "We are delighted to inform you that your vehicle purchase has been successfully confirmed.\n" + //
//                 "\n" + //
//                 "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" + //
//                 "\n" + //
//                 "PURCHASE DETAILS\n" + 
//                 "\nCustomer Name : " + "\n" + customer.getName() + 
//                 "\nBooking Date : " + "\n" + customer.getBookingDate() +
//                 "\nVehicles Purchased : "; 

//         for(Car car : customer.getCars()){
//             text +=  "\n" + car.getCompany() + " " + car.getModel();
//         }

//         text += "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" + //
//                         "\n" + //
//                         "Thank you for choosing Car Showroom.\n" + //
//                         "\n" + //
//                         "We sincerely appreciate your trust in us and hope you enjoy every journey in your new vehicle.\n" + //
//                         "\n" + //
//                         "Our team remains available to assist you with vehicle delivery, servicing, warranty support and any future requirements.\n" + //
//                         "\n" + //
//                         "We wish you many safe and memorable drives ahead.\n" + //
//                         "\n" + //
//                         "Warm Regards,\n" + //
//                         "\n" + //
//                         "Customer Relationship Team\n" + //
//                         "Car Showroom\n" + //
//                         "\n" + //
//                         "support@carshowroom.com\n" + //
//                         "www.carshowroom.com";

//         message.setText(text);

//         mailSender.send(message);
//     }

//     public void sendMarketingMail(Customer customer) {

//         SimpleMailMessage message = new SimpleMailMessage();

//         message.setTo(customer.getEmail());

//         message.setSubject("🚗 How's Your New Car? | Car Showroom");

//         String body = "Dear " + customer.getName() + ",\n\n"
//     + "We hope you're having a wonderful driving experience! As a valued member of the Car Showroom family, we're excited to bring you our latest offers and services.\n\n"
//     + "✔ Free Vehicle Health Check Camps\n"
//     + "✔ Exclusive Exchange Offers\n"
//     + "✔ Special Discounts on Accessories\n"
//     + "✔ Priority Service Booking\n"
//     + "✔ Latest Car Launches\n\n"
//     + "Visit us anytime—we'd love to assist you.\n\n"
//     + "Warm Regards,\n"
//     + "Customer Relationship Team\n"
//     + "Car Showroom\n"
//     + "support@carshowroom.com\n"
//     + "www.carshowroom.com";


//         message.setText(body);

//         mailSender.send(message);
//     }

// }







package com.actifyzone.carshowroom.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.actifyzone.carshowroom.entity.Car;
import com.actifyzone.carshowroom.entity.Customer;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${resend.from.email}")
    private String fromEmail;

    private final RestClient restClient = RestClient.create("https://api.resend.com");

    public void sendBookingMail(Customer customer) {

        String text = "Dear " + customer.getName() + "\nWarm Greetings from Car Showroom!\n" +
                "\n" +
                "We are delighted to inform you that your vehicle purchase has been successfully confirmed.\n" +
                "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "\n" +
                "PURCHASE DETAILS\n" +
                "\nCustomer Name : " + "\n" + customer.getName() +
                "\nBooking Date : " + "\n" + customer.getBookingDate() +
                "\nVehicles Purchased : ";

        for (Car car : customer.getCars()) {
            text += "\n" + car.getCompany() + " " + car.getModel();
        }

        text += "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "\n" +
                "Thank you for choosing Car Showroom.\n" +
                "\n" +
                "We sincerely appreciate your trust in us and hope you enjoy every journey in your new vehicle.\n" +
                "\n" +
                "Our team remains available to assist you with vehicle delivery, servicing, warranty support and any future requirements.\n" +
                "\n" +
                "We wish you many safe and memorable drives ahead.\n" +
                "\n" +
                "Warm Regards,\n" +
                "\n" +
                "Customer Relationship Team\n" +
                "Car Showroom\n" +
                "\n" +
                "support@carshowroom.com\n" +
                "www.carshowroom.com";

        send(customer.getEmail(), "🎉 Congratulations on Your New Car Purchase!", text);
    }

    public void sendMarketingMail(Customer customer) {

        String body = "Dear " + customer.getName() + ",\n\n"
                + "We hope you're having a wonderful driving experience! As a valued member of the Car Showroom family, we're excited to bring you our latest offers and services.\n\n"
                + "✔ Free Vehicle Health Check Camps\n"
                + "✔ Exclusive Exchange Offers\n"
                + "✔ Special Discounts on Accessories\n"
                + "✔ Priority Service Booking\n"
                + "✔ Latest Car Launches\n\n"
                + "Visit us anytime—we'd love to assist you.\n\n"
                + "Warm Regards,\n"
                + "Customer Relationship Team\n"
                + "Car Showroom\n"
                + "support@carshowroom.com\n"
                + "www.carshowroom.com";

        send(customer.getEmail(), "🚗 How's Your New Car? | Car Showroom", body);
    }

    private void send(String to, String subject, String plainTextBody) {

        Map<String, Object> payload = Map.of(
                "from", fromEmail,
                "to", List.of(to),
                "subject", subject,
                "text", plainTextBody
        );

        restClient.post()
                .uri("/emails")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }
}