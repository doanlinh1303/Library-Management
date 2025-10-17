package com.library.dto;

public class UserReportDTO {
    private Long id;
    private String userName;
    private String email;
    private int totalBorrowings;
    private int currentBorrowings;
    private int overdueItems;

    public UserReportDTO(Long id, String userName, String email, int totalBorrowings, int currentBorrowings, int overdueItems) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.totalBorrowings = totalBorrowings;
        this.currentBorrowings = currentBorrowings;
        this.overdueItems = overdueItems;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getTotalBorrowings() {
        return totalBorrowings;
    }

    public int getCurrentBorrowings() {
        return currentBorrowings;
    }

    public int getOverdueItems() {
        return overdueItems;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTotalBorrowings(int totalBorrowings) {
        this.totalBorrowings = totalBorrowings;
    }

    public void setCurrentBorrowings(int currentBorrowings) {
        this.currentBorrowings = currentBorrowings;
    }

    public void setOverdueItems(int overdueItems) {
        this.overdueItems = overdueItems;
    }
}
