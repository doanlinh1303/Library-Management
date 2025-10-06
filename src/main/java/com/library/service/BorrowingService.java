package com.library.service;

import com.library.model.Book;
import com.library.model.Borrowing;
import com.library.model.Borrowing.BorrowStatus;
import com.library.repository.IBookRepository;
import com.library.repository.IBorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {

    @Autowired
    private IBorrowingRepository borrowingRepository;

    @Autowired
    private IBookRepository bookRepository;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public Optional<Borrowing> getBorrowingById(Long id) {
        return borrowingRepository.findById(id);
    }

    public List<Borrowing> getBorrowingsByUserId(Long userId) {
        return borrowingRepository.findByUserId(userId);
    }

    public Borrowing returnBook(Long borrowingId, LocalDate returnDate) {
        // Tìm thông tin mượn sách
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        // Kiểm tra sách có đang được mượn không
        if (borrowing.getStatus() != BorrowStatus.ACTIVE) {
            throw new RuntimeException("Book is not currently borrowed or already returned");
        }

        // Cập nhật thông tin trả sách
        borrowing.setReturnDate(returnDate);
        borrowing.setStatus(BorrowStatus.RETURNED);

        // Lưu thông tin và cập nhật trạng thái sách
        return borrowingRepository.update(borrowing);
    }
    public void delete(Long id) {
        borrowingRepository.deleteById(id);
    }

    public List<Borrowing> getBookBorrowingHistory(Long bookId) {
        return borrowingRepository.findByBookId(bookId);
    }


    public Borrowing createBorrowing(Borrowing borrowing) {
        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> searchBorrowings(String status, Long user, String dateFrom, String dateTo) {
        return borrowingRepository.searchBorrowings(status, user, dateFrom, dateTo);
    }
}
