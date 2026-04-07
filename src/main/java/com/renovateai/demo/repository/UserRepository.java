package com.renovateai.demo.repository;

import com.renovateai.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email address
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find users by first name (case-insensitive)
     * @param firstName the first name to search for
     * @return list of users matching the first name
     */
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    /**
     * Find users by last name (case-insensitive)
     * @param lastName the last name to search for
     * @return list of users matching the last name
     */
    List<User> findByLastNameContainingIgnoreCase(String lastName);

    /**
     * Check if user exists with the given email
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find users by full name (first name and last name)
     * @param firstName the first name
     * @param lastName the last name
     * @return list of users matching both names
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) " +
           "AND LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))")
    List<User> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
