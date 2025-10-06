package com.library.repository;

import com.library.model.Borrowing;
import java.util.List;
import java.util.Optional;

public interface IBorrowingRepository {
    Borrowing save(Borrowing borrowing);
    Optional<Borrowing> findById(Long id);
    List<Borrowing> findAll();
    List<Borrowing> findByBookId(Long bookId);
    List<Borrowing> findByUserId(Long userId);
    List<Borrowing> findActiveByBookId(Long bookId);
    List<Borrowing> findByStatus(Borrowing.BorrowStatus status);
    Borrowing update(Borrowing borrowing);
    void deleteById(Long id);
    List<Borrowing> searchBorrowings(String status, Long user, String dateFrom, String dateTo);
    Long currentBorrowings();
    Long overDueCount();
}
