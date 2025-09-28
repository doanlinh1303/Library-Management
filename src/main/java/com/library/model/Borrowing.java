package com.library.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Borrowing {
    public enum Status {
        ACTIVE, RETURNED, OVERDUE
    }
    private Integer id;
    private Integer userId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Status status;
    private LocalDateTime createdAt;

    private Book book; // Associated Book object
    private User user; // Associated User object

    public Borrowing() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusWeeks(2); // Default due date is 2 weeks from borrow date
    }
    public Borrowing(Integer id, Integer userId, Long bookId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, Status status, LocalDateTime createdAt, Book book, User user) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.createdAt = createdAt;
        this.book = book;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
