package com.library.repository;

import com.library.model.Book;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IBookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    List<Book> findByCategory(String category);
    List<Book> searchBooks(String query, String category);
    Book update(Book book);
    void deleteById(Long id);
    List<String> findAllCategories();
    Long countBooks();
    Map<String, Long> countBooksByCategory();
    List<Book> findMostBorrowedBooks();
    List<Book> findRecentBooks(int count);
}
