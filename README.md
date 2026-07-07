# 📓 Journal Management API

A production-oriented **Spring Boot REST API** for managing personal journals with secure authentication, role-based authorization, intelligent caching, scheduled sentiment analysis, and comprehensive testing.

Designed to demonstrate backend development best practices, clean architecture, security, caching, and maintainable code.

---

## Features

* Secure authentication using **Spring Security**
* Role-Based Access Control (**Admin** & **User**)
* Complete CRUD operations for journal entries
* Redis caching for faster journal retrieval
* Dynamic application configuration cached from the database
* Weekly journal sentiment analysis using scheduled Cron Jobs
* Unit testing with **JUnit 5** and **Mockito**
* MongoDB integration
* Global exception handling with meaningful API responses
* Layered architecture following Spring Boot best practices

---

## 🛠️ Tech Stack

| Technology          | Purpose                        |
| ------------------- | ------------------------------ |
| Java 21             | Programming Language           |
| Spring Boot         | Backend Framework              |
| Spring Web          | REST APIs                      |
| Spring Security     | Authentication & Authorization |
| MongoDB             | Primary Database               |
| Redis               | Caching Layer                  |
| Spring Data MongoDB | Database Access                |
| JUnit 5             | Unit Testing                   |
| Mockito             | Mocking Framework              |
| Maven               | Dependency Management          |

---

## 📂 Project Structure

```text
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── cache
 ├── scheduler
 ├── config
 ├── exception
 ├── util
 └── resources
```

The project follows a clean layered architecture where each layer has a single responsibility, making the codebase easy to maintain and extend.

---

## 🔐 Authentication & Authorization

The application is secured using **Spring Security**.

### User Role

Users can:

* Create journals
* Read their journals
* Update their journals
* Delete their journals

### Admin Role

Administrators have elevated privileges for administrative endpoints protected using role-based authorization.

---

## ⚡ Redis Caching

Redis is integrated to improve application performance.

Caching is used for:

* Frequently accessed journal data
* Application configuration loaded from the database

This significantly reduces unnecessary database queries and improves response time.

---

## ⏰ Scheduled Jobs

A scheduled Cron Job runs **every Sunday** to analyze journal entries from the previous week.

The scheduler:

* Reads journal entries
* Performs sentiment analysis
* Generates insights that can be used for future features and analytics

---

## 🧪 Testing

The project includes unit tests written using:

* JUnit 5
* Mockito

Tests cover:

* Service Layer
* Business Logic
* Mocked Repository interactions
* Exception scenarios

---

## 🛡️ Exception Handling

A centralized global exception handler provides consistent API responses.

Examples include:

* Resource Not Found
* Invalid Requests
* Authentication & Authorization failures
* Internal Server Errors

---

## 📚 Core Functionalities

### Journal Management

* Create Journal
* Read Journal
* Update Journal
* Delete Journal

### User Management

* User Registration
* Authentication
* Role-based Authorization

### Performance

* Redis Cache
* Cached Application Configuration
* Optimized Database Access

---

## 🏗️ Architecture Overview

```text
                Client
                   │
                   ▼
          Spring Security
                   │
                   ▼
             REST Controllers
                   │
                   ▼
                Services
                   │
          ┌────────┴────────┐
          ▼                 ▼
      Redis Cache       MongoDB
```

---

## Highlights

* Production-style project structure
* Secure REST APIs
* Clean separation of concerns
* Efficient caching strategy
* Scheduled background processing
* Comprehensive unit testing
* Maintainable and scalable architecture

---

## Learning Outcomes

This project demonstrates practical experience with:

* Spring Boot
* Spring Security
* REST API Design
* MongoDB
* Redis
* Caching Strategies
* Scheduled Tasks (Cron Jobs)
* Unit Testing
* Mockito
* Exception Handling
* Layered Architecture
* Backend Performance Optimization

---

## Future Improvements

* JWT Authentication
* Docker Containerization
* API Documentation with Swagger/OpenAPI
* Email Notifications
* Metrics & Monitoring
* CI/CD Pipeline
* Rate Limiting
* Audit Logging

---

## Why this project?

This project goes beyond basic CRUD operations by incorporating security, caching, scheduled background jobs, testing, and clean architecture. It reflects production-oriented backend development practices while remaining modular, maintainable, and extensible.

If you found this project interesting, consider giving it a ⭐ on GitHub!
