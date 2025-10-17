package com.library.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private Boolean available;
    private String coverImageUrl;
    private LocalDateTime createdAt;
    private String description;
    private Integer publicationYear;
    private Integer pages;
    private String publisher;
    private String language;
    private Integer copies;
    private List<BorrowingDTO> borrowings;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public Integer getPages() { return pages; }
    public void setPages(Integer pages) { this.pages = pages; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Integer getCopies() { return copies; }
    public void setCopies(Integer copies) { this.copies = copies; }
    public List<BorrowingDTO> getBorrowings() { return borrowings; }
    public void setBorrowings(List<BorrowingDTO> borrowings) { this.borrowings = borrowings; }

    public BookDTO(Long id, String title, String author, String category, String isbn, Boolean available, String coverImageUrl, LocalDateTime createdAt, String description, Integer publicationYear, Integer pages, String publisher, String language, Integer copies, List<BorrowingDTO> borrowings) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.available = available;
        this.coverImageUrl = coverImageUrl;
        this.createdAt = createdAt;
        this.description = description;
        this.publicationYear = publicationYear;
        this.pages = pages;
        this.publisher = publisher;
        this.language = language;
        this.copies = copies;
        this.borrowings = borrowings;
    }
}
