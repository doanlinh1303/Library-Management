package com.library.service;

import com.library.model.Book;
import com.library.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private static final String UPLOAD_DIR = "/home/doanlinh/Downloads/Library-Management/uploads";

    @Autowired
    private IBookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book saveBook(Book book, MultipartFile coverImage) throws Exception {
        handleCoverImage(book, coverImage);
        if (book.getCreatedAt() == null) {
            book.setCreatedAt(LocalDateTime.now());
        }
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        return bookRepository.update(book);
    }

    public Book updateBook(Long id, Book book, MultipartFile coverImage) throws Exception {
        book.setId(id);
        handleCoverImage(book, coverImage);
        return bookRepository.update(book);
    }

    private void handleCoverImage(Book book, MultipartFile file) throws Exception {
        if (file != null && !file.isEmpty()) {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(dir, fileName);
            file.transferTo(dest);
            book.setCoverImageUrl("/uploads/" + fileName);
        }
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String query, String category) {
        return bookRepository.searchBooks(query, category);
    }

    public List<String> getAllCategories() {
        return bookRepository.findAllCategories();
    }

    public List<Book> getAvailableBooks() {
        List<Book> allBooks = getAllBooks();
        return allBooks.stream()
                .filter(Book::isAvailable)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Book> mostBorrowedBooks() {
        return bookRepository.findMostBorrowedBooks();
    }

    public List<Book> getRecentBooks(int count) {
        return bookRepository.findRecentBooks(count);
    }
}
