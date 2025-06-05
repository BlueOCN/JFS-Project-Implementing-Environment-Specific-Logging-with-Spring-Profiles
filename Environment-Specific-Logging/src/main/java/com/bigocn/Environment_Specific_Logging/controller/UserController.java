package com.bigocn.Environment_Specific_Logging.controller;

import com.bigocn.Environment_Specific_Logging.data.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/users")
public class UserController {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    // GET /users: Retrieve a list of all users.
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {

        LOGGER.info("Processing request: GET /users");

        if (users.isEmpty()) {
            LOGGER.warn("No users found in the system.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users);
        }

        try {
            LOGGER.info("Users retrieved successfully. Total users: {}", users.size());
            return ResponseEntity.ok(users);

        } catch (Exception ex) {
            LOGGER.error("Error while fetching users: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        LOGGER.info("Processing request: GET /users/{}", id);

        if (id == null || id <= 0) {
            LOGGER.warn("Invalid user ID: {}", id);
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid ID", "id", id));
        }

        LOGGER.debug("Searching for user with ID: {}", id);
        Optional<User> user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();

        if (user.isEmpty()) {
            LOGGER.warn("User not found: ID={}", id);
            Map<String, Object> errorDetails = Map.of(
                    "timestamp", Instant.now(),
                    "status", HttpStatus.NOT_FOUND.value(),
                    "error", "User Not Found",
                    "message", "No user found with ID " + id,
                    "path", "/users/" + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }
        LOGGER.info("Successfully retrieved user with ID: {}", id);
        return ResponseEntity.ok(user.get());
    }


    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        LOGGER.info("Processing request: POST /users");

        if (user == null) {
            LOGGER.warn("Received null user in request.");
            return ResponseEntity.badRequest().body(null);
        }

        try {
            user.setId(counter.incrementAndGet());
            users.add(user);

            LOGGER.info("User successfully added with ID: {}", user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        } catch (Exception ex) {
            LOGGER.error("Error while adding user: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        LOGGER.info("Processing request: DELETE /users/{}", id);

        if (id == null || id <= 0) {
            LOGGER.warn("Invalid user ID: {}", id);
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid ID", "id", id));
        }

        try {

            boolean removed = users.removeIf(user -> user.getId().equals(id));

            if (!removed) {
                LOGGER.warn("User not found: ID={}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "timestamp", Instant.now(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", "User Not Found",
                        "message", "No user found with ID " + id,
                        "path", "/users/" + id
                ));
            }

            LOGGER.info("Successfully deleted user with ID: {}", id);
            return ResponseEntity.noContent().build();

        } catch (Exception ex) {

            LOGGER.error("Error while deleting user ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", Instant.now(),
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Server Error",
                    "message", "Failed to delete user due to an internal error.",
                    "path", "/users/" + id
            ));

        }
    }

}
