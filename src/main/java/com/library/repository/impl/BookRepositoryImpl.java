package com.library.repository.impl;

import com.library.model.Book;
import com.library.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.HashMap;

import java.util.*;

@Repository
public class BookRepositoryImpl implements IBookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO books (title, author, category, isbn, available, description, publication_year, " +
                     "pages, publisher, language, copies, cover_image_url) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
            sql,
            book.getTitle(),
            book.getAuthor(),
            book.getCategory(),
            book.getIsbn(),
            book.isAvailable(),
            book.getDescription(),
            book.getPublicationYear(),
            book.getPages(),
            book.getPublisher(),
            book.getLanguage(),
            book.getCopies(),
            book.getCoverImageUrl()
        );

        // Lấy id mới nhất:
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        book.setId(id);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id=?";
        List<Book> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class), id);
        return list.stream().findFirst();
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books",
                new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public List<Book> findByCategory(String category) {
        return jdbcTemplate.query("SELECT * FROM books WHERE category=?",
                new BeanPropertyRowMapper<>(Book.class), category);
    }

    @Override
    public List<Book> searchBooks(String query, String category) {
        StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (query != null && !query.isBlank()) {
            sql.append(" AND (title LIKE ? OR author LIKE ? OR isbn LIKE ?)");
            String like = "%" + query + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" AND category=?");
            params.add(category);
        }

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Book.class), params.toArray());
    }



    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title=?, author=?, category=?, isbn=?, available=?, description=?, " +
                     "publication_year=?, pages=?, publisher=?, language=?, copies=?, cover_image_url=? WHERE id=?";

        jdbcTemplate.update(
            sql,
            book.getTitle(),
            book.getAuthor(),
            book.getCategory(),
            book.getIsbn(),
            book.isAvailable(),
            book.getDescription(),
            book.getPublicationYear(),
            book.getPages(),
            book.getPublisher(),
            book.getLanguage(),
            book.getCopies(),
            book.getCoverImageUrl(),
            book.getId()
        );
        return book;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }
    @Override
    public List<String> findAllCategories() {
        String sql = "SELECT DISTINCT category FROM books";
        return jdbcTemplate.queryForList(sql, String.class);
    }
    @Override
    public Long countBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
    @Override
    public Map<String, Long> countBooksByCategory() {
        String sql = "SELECT category, COUNT(*) AS total FROM books GROUP BY category";

        return jdbcTemplate.query(sql, rs -> {
            Map<String, Long> result = new HashMap<>();
            while (rs.next()) {
                String category = rs.getString("category");
                Long count = rs.getLong("total");
                result.put(category, count);
            }
            return result;
        });
    }
    @Override
    public List<Book> findMostBorrowedBooks() {
        String sql = "SELECT * FROM books ORDER BY borrowing_count DESC LIMIT 5";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }
    @Override
    public List<Book> findRecentBooks(int count) {
        String sql = "SELECT * FROM books ORDER BY created_at DESC LIMIT ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class), count);
    }
}
