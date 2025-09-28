package com.library.repository;

import com.library.model.Borrowing;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Borrowing entity
 * Implements Repository Pattern for data access abstraction
 */
public interface IBorrowingRepository {

    /**
     * Save a borrowing record
     * @param borrowing the borrowing to save
     * @return the saved borrowing with generated ID
     */
    Borrowing save(Borrowing borrowing);

    /**
     * Find a borrowing by ID
     * @param id the borrowing ID
     * @return Optional containing the borrowing if found
     */
    Optional<Borrowing> findById(Integer id);

    /**
     * Find all borrowings
     * @return list of all borrowings
     */
    List<Borrowing> findAll();

    /**
     * Find borrowings by user ID
     * @param userId the user ID
     * @return list of borrowings for the user
     */
    List<Borrowing> findByUserId(Integer userId);

    /**
     * Find borrowings by book ID
     * @param bookId the book ID
     * @return list of borrowings for the book
     */
    List<Borrowing> findByBookId(Integer bookId);

    /**
     * Find active borrowings by user ID
     * @param userId the user ID
     * @return list of active borrowings for the user
     */
    List<Borrowing> findActiveByUserId(Integer userId);

    /**
     * Find active borrowings by book ID
     * @param bookId the book ID
     * @return list of active borrowings for the book
     */
    List<Borrowing> findActiveByBookId(Integer bookId);

    /**
     * Find borrowings by status
     * @param status the status to filter by
     * @return list of borrowings with the specified status
     */
    List<Borrowing> findByStatus(Borrowing.Status status);

    /**
     * Find overdue borrowings
     * @return list of overdue borrowings
     */
    List<Borrowing> findOverdueBorrowings();

    /**
     * Find borrowings due on a specific date
     * @param dueDate the due date
     * @return list of borrowings due on the date
     */
    List<Borrowing> findByDueDate(LocalDate dueDate);

    /**
     * Find borrowings borrowed between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return list of borrowings in the date range
     */
    List<Borrowing> findByBorrowDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Update an existing borrowing
     * @param borrowing the borrowing to update
     * @return the updated borrowing
     */
    Borrowing update(Borrowing borrowing);

    /**
     * Delete a borrowing by ID
     * @param id the borrowing ID to delete
     */
    void deleteById(Integer id);

    /**
     * Check if a borrowing exists by ID
     * @param id the borrowing ID
     * @return true if exists, false otherwise
     */
    boolean existsById(Integer id);

    /**
     * Count total number of borrowings
     * @return total count
     */
    long count();

    /**
     * Count active borrowings by user
     * @param userId the user ID
     * @return count of active borrowings
     */
    long countActiveByUserId(Integer userId);
}
