package com.library.service;

import com.library.dto.UserReportDTO;
import com.library.model.Book;
import com.library.repository.IBookRepository;
import com.library.repository.IBorrowingRepository;
import com.library.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IBorrowingRepository borrowingRepository;

    public Long countTotalBooks() {
        return bookRepository.countBooks();
    }
    public Long activeUsers() {
        return userRepository.activeUsersCount();
    }
    public Long currentBorrowings() {
        return borrowingRepository.currentBorrowings();
    }
    public Long overDueBorrowings(){
        return borrowingRepository.overDueCount();
    }
    public Map<String, Long> countBooksByCategory() {
        return bookRepository.countBooksByCategory();
    }
    public List<Book> mostBorrowedBooks() {
        return bookRepository.findMostBorrowedBooks();
    }
    public List<UserReportDTO> getTopUsersByBorrowings() {
        return userRepository.generateUserReport();

    }
}
