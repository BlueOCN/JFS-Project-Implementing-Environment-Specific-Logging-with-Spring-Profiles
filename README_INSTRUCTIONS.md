# JFS-Project-Implementing-Environment-Specific-Logging-with-Spring-Profiles
You are tasked with creating a Spring Boot application that utilizes profiles (`dev`, `test`, `prod`) and environment-specific logging configurations. The application will:

- Serve as a basic REST API for managing a list of users.
- Configure different logging levels for each profile:
  - `dev`: Detailed `DEBUG` and `INFO` logs to monitor the application during development.
  - `test`: Minimal `INFO` logs for testing purposes.
  - `prod`: Only `WARN` and `ERROR` logs to avoid verbose output in production.
- Use `application-{profile}.properties` to configure the environment-specific settings.


## Objectives
By the end of this project, you should be able to:

- Use Spring Profiles to configure environment-specific settings.
- Implement environment-specific logging configurations.
- Understand the use of logging frameworks like Logback and how to customize logging levels.
- Use logs for monitoring and debugging an application in different environments (e.g., development, testing, production).

## Learning Outcomes
By completing this project, you will:

- Understand the purpose and implementation of Spring Profiles for environment-specific configurations.
- Learn to customize logging levels and outputs for different profiles.
- Gain experience in using logging for monitoring and debugging in development, testing, and production environments.
- Understand how to balance detailed logging in development with performance considerations in production.

## Instructions

### Step 1: Set Up the Project

1. Use `Spring Initializer` to generate a new Spring Boot project.
   - Dependencies: `Spring Web`, `Spring Boot Actuator`.
2. Import the project into your IDE (e.g., IntelliJ, Eclipse).

---

### Step 2: Create REST Endpoints

1. Build a simple REST API with endpoints for managing users:
   - `GET /users`: Retrieve a list of all users.
   - `POST /users`: Add a new user.
   - `DELETE /users/{id}`: Delete a user by ID.
2. Add basic logging statements in each endpoint to track application behavior:
   - Log `INFO` messages for successful operations.
   - Log `WARN` or `ERROR` messages for exceptional scenarios (e.g., invalid inputs or non-existent users).

---

### Step 3: Configure Profiles

1. Define profiles for `dev`, `test`, and `prod` by creating the following configuration files:
   - `application-dev.properties`
   - `application-test.properties`
   - `application-prod.properties`
2. Add environment-specific properties in each file, such as:
   - `server.port` for running on different ports.
   - `spring.datasource.url` for connecting to different databases (if applicable).
   - Profile-specific log levels.

---

### Step 4: Configure Logging
  
1. Use `Logback` (default in Spring Boot) to configure logging behavior:
   - Create a `logback-spring.xml` file in the `resources` directory.
   - Define logging configurations with placeholders for profiles (`${spring.profiles.active}`).
   - Example: Set the `dev` profile to log `DEBUG` and `INFO`, while the `prod` profile only logs `WARN` and `ERROR`.
2. Configure log file output for production:
   - Save logs to a file in the `logs` directory when the `prod` profile is active.
   - For `dev` and `test`, output logs to the console for easier debugging.

---

### Step 5: Test Profile and Logging Configurations

1. Use the `application.properties` file to set the active profile (`spring.profiles.active=dev/test/prod`).
2. Test each profile:
   - In the `dev` profile, verify that `DEBUG` logs are visible.
   - In the `test` profile, verify that only `INFO` logs are displayed.
   - In the `prod` profile, ensure that only `WARN` and `ERROR` logs are recorded and saved to a log file.
3. Use tools like `Postman` to trigger API endpoints and observe logs in the console or log files.

---

### Additional Challenges
To extend this project, you can:

- Integrate a centralized logging system, such as `ELK Stack` (`Elasticsearch`, `Logstash`, `Kibana`) or `Splunk`, for monitoring logs across environments.
- Use structured logging formats like `JSON` for easier log analysis.
- Add dynamic switching between profiles at runtime.
