package com.renovateai.demo.service;

import com.renovateai.demo.entity.User;
import com.renovateai.demo.exception.UserNotFoundException;
import com.renovateai.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * Create a new user
     * @param user the user to create
     * @return the created user
     * @throws IllegalArgumentException if email already exists
     */
    public User createUser(User user) {
        log.info("Creating new user with email: {}", user.getEmail());
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        try {
            User savedUser = userRepository.save(user);
            log.info("Successfully created user with id: {}", savedUser.getId());
            return savedUser;
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to create user due to data integrity violation: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to create user: " + e.getMessage());
        }
    }

    /**
     * Get user by ID
     * @param id the user ID
     * @return the user
     * @throws UserNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Get user by email
     * @param email the user email
     * @return the user
     * @throws UserNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    /**
     * Get all users
     * @return list of all users
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    /**
     * Search users by first name
     * @param firstName the first name to search for
     * @return list of matching users
     */
    @Transactional(readOnly = true)
    public List<User> searchUsersByFirstName(String firstName) {
        log.info("Searching users by first name: {}", firstName);
        return userRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    /**
     * Search users by last name
     * @param lastName the last name to search for
     * @return list of matching users
     */
    @Transactional(readOnly = true)
    public List<User> searchUsersByLastName(String lastName) {
        log.info("Searching users by last name: {}", lastName);
        return userRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    /**
     * Update an existing user
     * @param id the user ID
     * @param userDetails the updated user details
     * @return the updated user
     * @throws UserNotFoundException if user not found
     */
    public User updateUser(Long id, User userDetails) {
        log.info("Updating user with id: {}", id);
        
        User existingUser = getUserById(id);

        // Check if email is being changed and if new email already exists
        if (!existingUser.getEmail().equals(userDetails.getEmail()) && 
            userRepository.existsByEmail(userDetails.getEmail())) {
            throw new IllegalArgumentException("User with email " + userDetails.getEmail() + " already exists");
        }

        // Update fields
        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());

        try {
            User updatedUser = userRepository.save(existingUser);
            log.info("Successfully updated user with id: {}", id);
            return updatedUser;
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to update user due to data integrity violation: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to update user: " + e.getMessage());
        }
    }

    /**
     * Delete user by ID
     * @param id the user ID
     * @throws UserNotFoundException if user not found
     */
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
        log.info("Successfully deleted user with id: {}", id);
    }

    /**
     * Check if user exists by ID
     * @param id the user ID
     * @return true if user exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Check if user exists by email
     * @param email the user email
     * @return true if user exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
