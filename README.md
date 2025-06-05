# JFS-Project-Implementing-Environment-Specific-Logging-with-Spring-Profiles

---

## Overview
This documentation provides a structured guide to setting up, running, and managing a Spring Boot application with environment-specific logging profiles. The Setup & Run section outlines prerequisites, repository cloning, building, and running the application. It ensures that developers have the necessary tools and dependencies, making deployment straightforward.

The Models and API Endpoints sections explain the User model and available RESTful endpoints, including CRUD operations. Each endpoint is detailed with request and response formats to guide API consumption. This breakdown improves clarity for developers integrating this service into their applications.

The Profile-Specific Logging Configurations segment details environment-specific settings for dev, test, and prod, optimizing logging and database configurations for different scenarios. This ensures efficient debugging and performance management across various deployment environments.

## Index

- Setup & Run
  - Prerequisites
  - Clone the repository
  - Build the project
  - Running the application
- Models
  - User 
- API Endpoints
  - GET Users
  - GET User By Id
  - POST User
  - DELETE User
- Profile specific logging configurations
  - dev
  - test
  - prod

## Setup & Run

---

### 🔧 Prerequisites
Before setting up the project, ensure you have the following installed:
- **Java 17+** (Essential for running the application)
- **Maven** (For building and dependency management)
- **MySQL** (or another SQL database)
- **Git** (Version control for managing project versions)

---

### ✏️ Clone the repository

```shell
   git clone https://github.com/BlueOCN/JFS-Project-Implementing-Environment-Specific-Logging-with-Spring-Profiles.git
   cd Environment-Specific-Logging
```

---

### 🛠️ Build the project
```shell
  mvn clean install
```

---

### ▶️ Running the Application
```shell
  mvn spring-boot:run
```

## Models

---

In a Spring Boot project, a Model represents the application's data structure, typically mapped to a database entity using JPA. Models define attributes and relationships, ensuring data consistency. In REST controllers, models are used as request and response objects, enabling CRUD operations via HTTP methods. Controllers handle incoming requests, process data using models, and return structured responses in JSON format. This separation of concerns improves maintainability, scalability, and readability in RESTful APIs

### User

| Field	 | Type	   | Description                     |
|--------|---------|---------------------------------|
| id	    | Long	   | Unique identifier for the user. |
| name	  | String	 | User's full name.               |
| email	 | String	 | User's email address.           |

This User model represents a simple entity with three attributes: id, name, and email. It includes getter and setter methods for accessing and modifying these fields. The equals and hashCode methods ensure proper comparison and hashing based on the object's attributes. The toString method provides a readable string representation of the user. This model is useful for managing user data in a Spring Boot application, allowing easy integration with REST controllers and database operations.


## API Endpoints

---

| HTTP Method | 	Endpoint	    | Description                     |
|-------------|---------------|---------------------------------|
| GET         | 	/users	      | Retrieve a list of all users.   |
| GET         | 	/users/{id}	 | Retrieve a specific user by ID. |
| POST        | 	/users	      | Add a new user to the system.   |
| DELETE      | 	/users/{id}	 | Delete a user by ID.            |

### Retrieve a list of all users.
This method handles a GET request to /users, retrieving all registered users. It logs the request and checks if the user list is empty. If no users exist, it returns 204 No Content. Otherwise, it attempts to fetch the user list and logs the total count. If successful, it responds with 200 OK and the user list. In case of an exception, it logs the error and returns 500 Internal Server Error. The logging ensures visibility into request processing and potential issues.

**GET** `/users`
- Response body:
```json
[
  {
    "id": 1,
    "name": "Julio",
    "email": "julio@email.com"
  }
]
```

---

### Retrieve a specific user by ID.
This method handles a GET request to /users/{id} to fetch a user by ID. It first logs the request and validates the ID. If invalid, it returns 400 Bad Request. The method searches the user list for a matching ID, logging the process. If no user is found, it returns 404 Not Found with error details. If successful, it logs the retrieval and returns 200 OK with the user data. Exception handling ensures reliable responses and system transparency.

**GET** `/users/{id}`
- Response body:
```json
{
  "id": 1,
  "name": "Julio",
  "email": "julio@email.com"
}
```

---

### Add a new user to the system.
This method handles a POST request to /users to create a new user. It logs the request and validates that the user is not null. If null, it returns 400 Bad Request. The method assigns a unique ID to the user, adds them to the list, and logs the successful addition. If successful, it responds with 201 Created along with the user data. In case of an error, it logs the issue and returns 500 Internal Server Error. The logging helps track processing events and ensures transparency in user creation.

**POST** `/users`
- Request body:
```json
{
  "id": 9,
  "name": "Julio",
  "email": "julio@email.com"
}
```
- Response body:
```json
{
  "id": 1,
  "name": "Julio",
  "email": "julio@email.com"
}
```

---

### Delete a user by ID.
This method handles a DELETE request to /users/{id} to remove a user by ID. It first logs the request and validates the ID, returning 400 Bad Request if invalid. It then attempts to remove the user from the list. If the user is not found, it returns 404 Not Found with error details. If successful, it logs the deletion and returns 204 No Content. In case of an exception, it logs the error and responds with 500 Internal Server Error. The logging ensures visibility into request processing and potential issues.

**DELETE** `/users/{id}`
- No Response body


## Profile specific logging configurations

---

Spring Boot Profiles allow applications to manage different configurations for various environments, such as development, testing, and production. By using the @Profile annotation or setting spring.profiles.active, developers can activate specific configurations dynamically. This prevents hardcoding environment-specific values, making applications more flexible and maintainable. Profiles help streamline deployment, ensuring the right settings are applied without manual intervention. They also improve security by isolating sensitive configurations. Overall, profiles enhance scalability and simplify environment management, making Spring Boot applications more adaptable

### Global configuration
This Spring Boot configuration sets the application name and version while defining environment-specific profiles. The spring.profiles.default=dev ensures the default profile is "dev," while spring.profiles.active=dev explicitly activates it. This allows switching configurations dynamically for different environments. The management.info.app.name and management.info.app.version provide metadata for monitoring and management tools. These settings help streamline deployment, ensuring the correct profile is used without manual intervention, improving flexibility and maintainability.

```properties
spring.application.name=Implementing-Environment-Specific-Logging-with-Spring-Profiles
spring.application.version=1.0.0

spring.profiles.default=dev
spring.profiles.active=dev # Change the active configuration to run the desired profile

management.info.app.name=Environment-Specific-Logging
management.info.app.version=1.0.0
```

### Dev Profile
This Spring Boot configuration sets the server port to 8081 and connects to a local MySQL database (dev_db) using the specified credentials. Hibernate's ddl-auto=update ensures automatic schema updates. Logging is set to DEBUG for detailed debugging across Spring components and the application's controllers. The logs are stored in logs/app-dev.log, helping track application behavior during development. This setup optimizes debugging and database management for local development.

```properties
server.port=8081

# Local development database
spring.datasource.url=jdbc:mysql://localhost:3306/dev_db
spring.datasource.username=dev_user
spring.datasource.password=dev_password
spring.jpa.hibernate.ddl-auto=update

# Enable detailed logging for debugging
logging.level.root=DEBUG
logging.level.org.springframework=DEBUG
logging.level.com.bigocn.Environment_Specific_Logging=DEBUG
logging.level.com.bigocn.Environment_Specific_Logging.controller=DEBUG
logging.level.com.bigocn.Environment_Specific_Logging.controller.UserController=DEBUG

logging.file.name=logs/app-dev.log
```

### Test Profile
This Spring Boot configuration sets the server port to 8082 and uses an in-memory H2 database (testdb) for testing. The ddl-auto=create-drop ensures the schema is created at startup and dropped when the application stops. Logging is set to INFO to reduce unnecessary details, making test reports clearer. The logs are stored in logs/app-test.log, helping track application behavior during testing. This setup optimizes database management and debugging for test environments.

```properties
server.port=8082

# Use H2 for testing
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop

# Reduce logging noise for better test reports
logging.level.root=INFO
logging.level.org.springframework=INFO
logging.level.com.bigocn.Environment_Specific_Logging=INFO
logging.level.com.bigocn.Environment_Specific_Logging.controller=INFO
logging.level.com.bigocn.Environment_Specific_Logging.controller.UserController=INFO

logging.file.name=logs/app-test.log
```

### Prod Profile
This Spring Boot configuration sets the server port to 8080 and exposes only health and info endpoints while hiding health details for security. It connects to a remote MySQL production database (prod_db) with strict validation (ddl-auto=validate) to prevent unintended schema changes. Logging is optimized for performance by setting levels to WARN, reducing unnecessary logs. The logs are stored in logs/app-prod.log, ensuring efficient monitoring. This setup enhances security, stability, and performance for production environments.

```properties
server.port=8080

management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=never

# Production database (remote server)
spring.datasource.url=jdbc:mysql://prod-db-server:3306/prod_db
spring.datasource.username=prod_user
spring.datasource.password=secure_password
spring.jpa.hibernate.ddl-auto=validate

# Optimize logging for performance
logging.level.root=WARN
logging.level.org.springframework=WARN
logging.level.com.bigocn.Environment_Specific_Logging.controller=WARN

logging.file.name=logs/app-prod.log
```