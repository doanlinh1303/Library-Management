package com.library.repository.impl;

import com.library.model.Book;
import com.library.repository.IBookRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements IBookRepository {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(idGenerator.getAndIncrement());
        }
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public List<Book> findByTitleContainingIgnoreCase(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle() != null &&
                        book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthorContainingIgnoreCase(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor() != null &&
                        book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByCategory(String category) {
        return books.values().stream()
                .filter(book -> Objects.equals(book.getCategory(), category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAvailableTrue() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return books.values().stream()
                .filter(book -> Objects.equals(book.getIsbn(), isbn))
                .findFirst();
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == null || !books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Book with ID " + book.getId() + " does not exist");
        }
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public void deleteById(Long id) {
        books.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return books.containsKey(id);
    }

    @Override
    public long count() {
        return books.size();
    }
}
