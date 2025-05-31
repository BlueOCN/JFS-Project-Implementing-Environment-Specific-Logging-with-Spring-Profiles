package com.bigocn.Environment_Specific_Logging.controller;

import com.bigocn.Environment_Specific_Logging.data.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
        LOGGER.info("Fetching all users");

        try {
            if (users.isEmpty()) {
                LOGGER.warn("No users found in the system");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users);
            }
            return ResponseEntity.ok(users);
        } catch (Exception ex) {
            LOGGER.error("Error fetching users: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET /users/{id}: Retrieve a specific user by ID.
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        LOGGER.info("Fetching user with ID: {}", id);
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.warn("User with ID {} not found", id);
                    return new RuntimeException("User not found");
                });
    }

    // POST /users: Add a new user.
    @PostMapping
    public User addUser(@RequestBody User user) {
        try {
            user.setId(counter.incrementAndGet());
            users.add(user);
            LOGGER.info("Added new user: {}", user);
            return user;
        } catch (Exception ex) {
            LOGGER.error("Error adding user: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to add user");
        }
    }

    // DELETE /users/{id}:
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        try {
            boolean removed = users.removeIf(user -> user.getId().equals(id));
            if (removed) {
                LOGGER.info("Deleted user with ID: {}", id);
            } else {
                LOGGER.warn("Attempted to delete non-existent user with ID: {}", id);
                throw new RuntimeException("User not found");
            }
        } catch (Exception ex) {
            LOGGER.error("Error deleting user: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete user");
        }
    }
}
