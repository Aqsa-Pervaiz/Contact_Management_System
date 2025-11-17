# Contact Management System

## Overview

The **Contact Management System** is a Spring Boot + React-based application that allows users to manage and organize their contacts. The system enables login using either **email or phone number** and provides features like adding, updating, and deleting contacts. It also integrates testing tools such as **JUnit & Mockito** and quality analysis with **SonarQube**.

---

## Features

### **User Authentication**

* Login using **email or phone number**.
* Basic authentication flow.
* Session-based login.

### **Contact Management**

* Create new contacts.
* Edit and update contact information.
* Delete contacts.
* Simple list view of contacts.

### **Dashboard**

* Basic user dashboard.

### **Code Quality & Testing**

* Static code analysis with **SonarQube**.
* Unit testing with **JUnit 5**.
* Mocking with **Mockito**.

## Technologies Used

* **Frontend:** HTML5, CSS3
* **Backend:** Spring Boot, Spring MVC, Spring Security
* **Database:** MySQL / H2 (for testing)
* **Build Tool:** Maven

---

## Project Structure


```
contact-management-system/
├── frontend/                         # React client
│   ├── node_modules/
│   ├── public/
│   └── src/
│       ├── App.css
│       ├── App.js
│       ├── App.test.js
│       ├── ContactManagementScreen.css
│       ├── ContactManagementScreen.js
│       ├── index.css
│       ├── index.js
│       ├── LoginPage.css
│       ├── LoginPage.jsx
│       ├── Navbar.css
│       ├── Navbar.jsx
│       ├── reportWebVitals.js
│       ├── setupTests.js
│       ├── UserProfile.css
│       └── UserProfile.jsx
│
├── src/                               # Spring Boot backend
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── entity/            # All Java files placed here
│   │   │       │   ├── Contact.java
│   │   │       │   ├── ContactController.java
│   │   │       │   ├── ContactRepository.java
│   │   │       │   ├── Email.java
│   │   │       │   ├── LabeledValue.java
│   │   │       │   ├── User.java
│   │   │       │   ├── UserController.java
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── WebMvcTest.java
│   │   │       │   └── DemoApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/example/demo/
│               ├── ContactControllerTest.java
│               ├── ContactRepositoryTest.java
│               ├── DemoApplicationTests.java
│               ├── UserControllerTest.java
│               └── UserRepositoryTest.java
│
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
├── sonar.properties
└── README.md

```


## Entity Relationship Diagram

* **User** (1) ---- (Many) **Contact**
* A user can have multiple contacts.
* Contact belongs to a single user.

---

## Installation & Setup Instructions

### **1. Clone the Repository**

```
git clone https://github.com/your-username/contact-management-system.git
cd contact-management-system
```

### **2. Configure Database**

Update your `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/contactdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### **3. Build the Project**

```
mvn clean install
```

### **4. Run the Application**

```
mvn spring-boot:run
```

The application will start on: **[http://localhost:8081](http://localhost:8081)**

---

## Security Features

* Basic login system using email or phone number.
* Session-based authentication.
* Simple access restrictions for authenticated users only.


## Screenshots 

<img width="1915" height="849" alt="image" src="https://github.com/user-attachments/assets/7d6a62fc-11ec-482d-b04b-aa7b10c7d89d" />
<img width="1918" height="925" alt="image" src="https://github.com/user-attachments/assets/815d681f-5236-4013-b5b1-dc85699fac40" />
<img width="1909" height="880" alt="image" src="https://github.com/user-attachments/assets/2f7c19a0-2b11-401f-b2e3-65b417a5c35b" />


