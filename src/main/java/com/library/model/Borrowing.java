package com.library.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Borrowing {
    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowStatus status;
    private LocalDateTime createdAt;

    // Thông tin phụ
    private Book book;
    private User user;

    public enum BorrowStatus {
        ACTIVE, RETURNED, OVERDUE
    }

    public Borrowing() {
        this.createdAt = LocalDateTime.now();
        this.status = BorrowStatus.ACTIVE;
    }

    public Borrowing(Long bookId, Long userId, LocalDate borrowDate, LocalDate dueDate, BorrowStatus status) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
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

    public boolean isOverdue() {
        if (returnDate != null) {
            return false; // Đã trả sách
        }
        return LocalDate.now().isAfter(dueDate);
    }

    public String getStatusDisplay() {
        if (status == BorrowStatus.RETURNED) {
            return "RETURNED";
        } else if (isOverdue()) {
            return "OVERDUE";
        } else {
            return "ACTIVE";
        }
    }
}
