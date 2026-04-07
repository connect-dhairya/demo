package com.renovateai.demo.controller;

import com.renovateai.demo.entity.User;
import com.renovateai.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    /**
     * Create a new user
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("REST request to create User: {}", user.getEmail());
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Get all users
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("REST request to get all Users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("REST request to get User by id: {}", id);
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Update an existing user
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        log.info("REST request to update User with id: {}", id);
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user by ID
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("REST request to delete User with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search users by first name
     * GET /api/users/search/first-name?name={firstName}
     */
    @GetMapping("/search/first-name")
    public ResponseEntity<List<User>> searchUsersByFirstName(@RequestParam String name) {
        log.info("REST request to search Users by first name: {}", name);
        List<User> users = userService.searchUsersByFirstName(name);
        return ResponseEntity.ok(users);
    }

    /**
     * Search users by last name
     * GET /api/users/search/last-name?name={lastName}
     */
    @GetMapping("/search/last-name")
    public ResponseEntity<List<User>> searchUsersByLastName(@RequestParam String name) {
        log.info("REST request to search Users by last name: {}", name);
        List<User> users = userService.searchUsersByLastName(name);
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by email
     * GET /api/users/email?email={email}
     */
    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        log.info("REST request to get User by email: {}", email);
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Check if user exists by ID
     * GET /api/users/{id}/exists
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkUserExists(@PathVariable Long id) {
        log.info("REST request to check if User exists with id: {}", id);
        boolean exists = userService.existsById(id);
        return ResponseEntity.ok(exists);
    }
}
