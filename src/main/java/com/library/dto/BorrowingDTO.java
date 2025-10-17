package com.library.dto;

import java.time.LocalDate;

public class BorrowingDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private String userName;
    private String userEmail;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BorrowingDTO(Long id, Long bookId, Long userId, String userName, String userEmail, java.time.LocalDate borrowDate, java.time.LocalDate dueDate, java.time.LocalDate returnDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }
}
