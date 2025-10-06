//package com.library.repository;
//
//import com.library.model.Book;
//import com.library.repository.impl.BookRepositoryImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//
//class BookRepositoryImplTest {
//    private BookRepositoryImpl repo;
//
//    @BeforeEach
//    void setUp() {
//        repo = new BookRepositoryImpl();
//    }
//
//    @Test
//    void testSaveAndFindById() {
//        Book book = new Book(null, "Title", "Author", "Category", "ISBN", true, "url", LocalDateTime.now());
//        Book saved = repo.save(book);
//        Optional<Book> found = repo.findById(saved.getId());
//        assertTrue(found.isPresent());
//        assertEquals("Title", found.get().getTitle());
//        System.out.println("Tên sách tìm theo ID: " + found.get().getTitle());
//    }
//
//    @Test
//    void testFindAll() {
//        repo.save(new Book(null, "Title1", "Author1", "Category1", "ISBN1", true, "url1", LocalDateTime.now()));
//        repo.save(new Book(null, "Title2", "Author2", "Category2", "ISBN2", true, "url2", LocalDateTime.now()));
//        List<Book> books = repo.findAll();
//        assertEquals(2, books.size());
//        System.out.print("Danh sách tên sách: ");
//        books.forEach(b -> System.out.print(b.getTitle() + ", "));
//        System.out.println();
//    }
//
//    @Test
//    void testFindByTitleContainingIgnoreCase() {
//        repo.save(new Book(null, "Java Programming", "AuthorA", "CatA", "ISBN-A", true, "urlA", LocalDateTime.now()));
//        repo.save(new Book(null, "Spring Boot", "AuthorB", "CatB", "ISBN-B", true, "urlB", LocalDateTime.now()));
//        List<Book> books = repo.findByTitleContainingIgnoreCase("java");
//        assertEquals(1, books.size());
//        assertEquals("Java Programming", books.get(0).getTitle());
//        System.out.print("Tên sách tìm theo tiêu đề: ");
//        books.forEach(b -> System.out.print(b.getTitle() + ", "));
//        System.out.println();
//    }
//
//    @Test
//    void testFindByAuthorContainingIgnoreCase() {
//        repo.save(new Book(null, "BookA", "Nguyen Van A", "CatA", "ISBN-A", true, "urlA", LocalDateTime.now()));
//        repo.save(new Book(null, "BookB", "Tran Van B", "CatB", "ISBN-B", true, "urlB", LocalDateTime.now()));
//        List<Book> books = repo.findByAuthorContainingIgnoreCase("nguyen");
//        assertEquals(1, books.size());
//        assertEquals("Nguyen Van A", books.get(0).getAuthor());
//        System.out.print("Tên tác giả tìm theo tên: ");
//        books.forEach(b -> System.out.print(b.getAuthor() + ", "));
//        System.out.println();
//    }
//
//}
