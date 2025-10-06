package com.library.model;
import java.time.LocalDateTime;
import java.util.Objects;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private boolean available;
    private String coverImageUrl;
    private LocalDateTime createdAt;

    // Thêm các trường mới
    private String description;
    private Integer publicationYear;
    private Integer pages;
    private String publisher;
    private String language;
    private Integer copies;
    private Long borrowingCount;

 

    public Book() {
        this.createdAt = LocalDateTime.now();
        this.available = true;
        this.copies = 1;
    }

    public Book(Long id, String title, String author, String category, String isbn, boolean available, String coverImageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.available = available;
        this.coverImageUrl = coverImageUrl;
        this.copies = 1;
    }

    // Constructor đầy đủ với các trường mới
    public Book(String title, String author, String category, String isbn, boolean available, String coverImageUrl,
                LocalDateTime createdAt, String description, Integer publicationYear, Integer pages, String publisher,
                String language, Integer copies, Long borrowingCount) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.available = available;
        this.coverImageUrl = coverImageUrl;
        this.createdAt =  LocalDateTime.now();
        this.description = description;
        this.publicationYear = publicationYear;
        this.pages = pages;
        this.publisher = publisher;
        this.language = language;
        this.copies = copies;
        this.borrowingCount = borrowingCount;
    }

    public Long getId() {
        return id;
    }

    public Long getBorrowingCount() {
        return borrowingCount;
    }

    public void setBorrowingCount(Long borrowingCount) {
        this.borrowingCount = borrowingCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Thêm getter/setter cho các trường mới
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    // Đảm bảo phương thức getAvailable() được định nghĩa (cho Thymeleaf)
    public boolean getAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return  Objects.equals(id, book.id) &&
                Objects.equals(isbn, book.isbn);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn);
    }
}
