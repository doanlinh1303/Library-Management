package com.library.repository;

import com.library.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity
 * Implements Repository Pattern for data access abstraction
 */
public interface IUserRepository {

    /**
     * Save a user to the repository
     * @param user the user to save
     * @return the saved user with generated ID
     */
    User save(User user);

    /**
     * Find a user by ID
     * @param id the user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Integer id);

    /**
     * Find a user by username
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find all users
     * @return list of all users
     */
    List<User> findAll();

    /**
     * Find users by role
     * @param role the role to filter by
     * @return list of users with the specified role
     */
    List<User> findByRole(User.Role role);

    /**
     * Find users by status
     * @param status the status to filter by
     * @return list of users with the specified status
     */
    List<User> findByStatus(User.Status status);

    /**
     * Find active users
     * @return list of active users
     */
    List<User> findActiveUsers();

    /**
     * Find users by full name containing (case insensitive)
     * @param name the name to search for
     * @return list of matching users
     */
    List<User> findByFullNameContainingIgnoreCase(String name);

    /**
     * Find user by email
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Update an existing user
     * @param user the user to update
     * @return the updated user
     */
    User update(User user);

    /**
     * Delete a user by ID
     * @param id the user ID to delete
     */
    void deleteById(Integer id);

    /**
     * Check if a user exists by ID
     * @param id the user ID
     * @return true if exists, false otherwise
     */
    boolean existsById(Integer id);

    /**
     * Check if username is already taken
     * @param username the username to check
     * @return true if taken, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if email is already taken
     * @param email the email to check
     * @return true if taken, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Count total number of users
     * @return total count
     */
    long count();
}
