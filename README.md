# 🎯 EduPulse - Quiz & Assessment Service
> The intelligent assessment engine for creating, delivering, and evaluating student quizzes with automated grading and detailed analytics.

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/)
[![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![OpenFeign](https://img.shields.io/badge/OpenFeign-00ADD8?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-cloud-openfeign)
[![Microservices](https://img.shields.io/badge/Architecture-Microservices-blue?style=for-the-badge)](#)

---

## 📖 Project Overview

The **Quiz Service** powers the assessment capabilities of EduPulse, enabling lecturers to create comprehensive quizzes with multiple-choice questions and providing students with an interactive testing experience. The service features automatic grading, detailed result tracking, and performance analytics.

Built with **intelligent scoring algorithms** and **real-time validation**, this service ensures fair assessment while providing immediate feedback to students and comprehensive analytics to lecturers.

### 🏗 Microservices Intercommunication

This service enhances the learning experience through strategic integrations:

* **📚 Lecture Association:** Links quizzes to specific lectures via `Class-Service`.
* **👤 Identity Verification:** Validates student and lecturer credentials through `User-Service`.
* **📊 Performance Analytics:** Provides quiz statistics to `Admin-Service` for platform insights.
* **📝 Enrollment Check:** Ensures only enrolled students can attempt class quizzes.

---

## 🚀 Key Features

* **✏️ Quiz Creation:** Lecturers can build quizzes with multiple questions and answer options.
* **⚡ Automatic Grading:** Instant scoring based on correct answers with percentage calculation.
* **📊 Result Tracking:** Comprehensive quiz attempt history with detailed answer breakdowns.
* **🎯 Student Performance:** Personal statistics including average scores and completion rates.
* **👨‍🏫 Lecturer Analytics:** View all student results per quiz with performance insights.
* **📝 Question Management:** Support for multiple-choice questions with 4 answer options.
* **🔍 Attempt Details:** Students can review their answers with correct/incorrect indicators.
* **⏰ Time Tracking:** Record submission times and attempt duration.
* **🔒 One Attempt Policy:** Prevents multiple submissions per student per quiz.

---

## 🛠 Tech Stack

* **Backend:** Java 21, Spring Boot 3.5.0
* **Security:** Spring Security 6.x, JWT Authentication
* **Database:** MySQL with JPA/Hibernate
* **Build Tool:** Maven
* **Inter-Service Comm:** OpenFeign (User Service & Class Service)
* **Validation:** Hibernate Validator
* **DevOps:** Spring DevTools

---

## 📡 API Documentation (V1)

### ✏️ Quiz Management (Lecturer)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/quizzes` | Create a new quiz for a lecture | Lecturer |
| `GET` | `/api/quizzes/{quizId}` | Get quiz details by ID | Lecturer/Student |
| `GET` | `/api/quizzes/lecture/{lectureId}` | Get all quizzes for a lecture | Lecturer/Student |

### 📝 Student Assessment

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/quizzes/submit` | Submit quiz answers and get results | Student |
| `GET` | `/api/quizzes/{quizId}/my-result` | Get my result for a specific quiz | Student |
| `GET` | `/api/quizzes/{quizId}/attempt-details` | Get detailed attempt with correct answers | Student |
| `GET` | `/api/quizzes/my-statistics` | Get personal quiz statistics | Student |

### 📊 Results & Analytics

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/quizzes/{quizId}/results` | Get all results for a quiz | Lecturer |
| `GET` | `/api/quizzes/student/{studentId}/results` | Get all quiz results for a student | Lecturer |

### 🔢 Platform Statistics

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/quizzes/count` | Get total quizzes count | Admin |

---

- ## 🔗 Related Services

- [🌐 API Gateway](https://github.com/Bavinduyeshan/Edu-Pulse-Gateway)
-  [👤 User Service](https://github.com/Bavinduyeshan/Edu-Pulse-User-Service)
- [📚 Class Service](https://github.com/Bavinduyeshan/Edu-Pulse_Class_Service)
- [📝 Enrollment Service](https://github.com/Bavinduyeshan/Edu-Pulse-Entrollment-Service)
- [🎯 Quiz Service](https://github.com/Bavinduyeshan/Edu-Pulse-Quiz_Service)
- [👨‍💼 Admin Service](https://github.com/Bavinduyeshan/Edu-Pulse_Admin_Service)

---

<div align="center">

**Built with ❤️ for better education management**

⭐ Star this repository if you find it helpful!

</div>
