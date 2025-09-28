package com.library.repository;

import com.library.model.Book;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book entity
 * Implements Repository Pattern for data access abstraction
 */
public interface IBookRepository {

    /**
     * Save a book to the repository
     * @param book the book to save
     * @return the saved book with generated ID
     */
    Book save(Book book);

    /**
     * Find a book by its ID
     * @param id the book ID
     * @return Optional containing the book if found
     */
    Optional<Book> findById(Long id);

    /**
     * Find all books
     * @return list of all books
     */
    List<Book> findAll();

    /**
     * Find books by title (case insensitive)
     * @param title the title to search for
     * @return list of matching books
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Find books by author (case insensitive)
     * @param author the author to search for
     * @return list of matching books
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);

    /**
     * Find books by category
     * @param category the category to search for
     * @return list of matching books
     */
    List<Book> findByCategory(String category);

    /**
     * Find available books
     * @return list of available books
     */
    List<Book> findByAvailableTrue();

    /**
     * Find books by ISBN
     * @param isbn the ISBN to search for
     * @return Optional containing the book if found
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Update an existing book
     * @param book the book to update
     * @return the updated book
     */
    Book update(Book book);

    /**
     * Delete a book by ID
     * @param id the book ID to delete
     */
    void deleteById(Long id);

    /**
     * Check if a book exists by ID
     * @param id the book ID
     * @return true if exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Count total number of books
     * @return total count
     */
    long count();
}
