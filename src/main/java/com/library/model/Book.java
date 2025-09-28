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

    public Book() {
        this.createdAt = LocalDateTime.now();
        this.available = true;
    }

    public Book(Long id, String title, String author, String category, String isbn, boolean available, String coverImageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.available = available;
        this.coverImageUrl = coverImageUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
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
