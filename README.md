# 🚗 Car Showroom Management System

A full-stack Car Showroom Management System built using **Spring Boot** and **PostgreSQL**. The application allows efficient management of customers, cars, bookings, and users while implementing secure authentication and role-based authorization.And for a info this is my Peak project I have ever worked on hence it is having too many features.

---

## 📌 Features

- Customer Management (Add, Update, Delete, View)
- Car Management
- Customer-Car Booking Relationship
- JWT Token Authentication
- Role-Based Authorization (Owner & Employee)
- Search Customers
- Dashboard Statistics
- Pagination
- Sorting
- Filtering
- RESTful APIs
- PostgreSQL Database Integration

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

### Database
- PostgreSQL

### Tools
- DBeaver
- Postman
- IntelliJ IDEA
- Git & GitHub

---

## 📂 Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── config
 ├── security
 └── resources
```

---

## 🔐 Authentication

The application uses:

- JWT Authentication
- Spring Security
- Role Based Access Control

Roles:

- OWNER
- EMPLOYEE

Only Owners can perform sensitive operations like deleting users or customers.

---

## 📋 API Features

### Customer APIs

- Create Customer
- Update Customer
- Delete Customer
- Get Customer Details
- Get All Customers
- Search Customers
- Customer Count

### Car APIs

- Add Car
- View Cars
- Car Count

### User APIs

- Register
- Login
- Logout
- Profile

---

## 📈 Additional Features

- Pagination
- Sorting
- Filtering
- Search by:
  - Name
  - Email
  - Booking Date
  - Customer ID

---

## ⚙️ Installation

Clone the repository

```bash
git clone https://github.com/yourusername/car-showroom-management-system.git
```

Move into the project

```bash
cd car-showroom-management-system
```

Configure PostgreSQL in

```
application.properties
```

Run the application

```bash
mvn spring-boot:run
```

---

## 🗄️ Database

Database Used:

- PostgreSQL

ORM:

- Hibernate
- Spring Data JPA

---

## 🚀 Future Improvements

- Image Upload for Cars
- Email Notifications
- Payment Integration
- Reports & Analytics
- React Frontend
- Docker Deployment

---

## 👨‍💻 Author

**Mohammad Talha Khan**

GitHub:
https://github.com/mohdtalhak
