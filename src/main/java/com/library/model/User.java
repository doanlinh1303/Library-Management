package com.library.model;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private UserRole role;
    private UserStatus status;
    private String profileImageUrl;
    private LocalDateTime createdAt;

    public enum UserRole {
        ADMIN, LIBRARIAN, USER
    }

    public enum UserStatus {
        ACTIVE, INACTIVE, BLOCKED
    }

    public User() {
        this.createdAt = LocalDateTime.now();
        this.role = UserRole.USER;
        this.status = UserStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatusDisplay() {
        return status.toString();
    }

    public String getRoleDisplay() {
        return role.toString();
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }
}
