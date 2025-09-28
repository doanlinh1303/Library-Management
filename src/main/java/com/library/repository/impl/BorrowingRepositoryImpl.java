package com.library.repository.impl;

import com.library.model.Borrowing;
import com.library.repository.IBorrowingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class BorrowingRepositoryImpl implements IBorrowingRepository {
    private final Map<Integer, Borrowing> borrowings = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Borrowing save(Borrowing borrowing) {
        if (borrowing.getId() == null) {
            borrowing.setId(idGenerator.getAndIncrement());
        }
        borrowings.put(borrowing.getId(), borrowing);
        return borrowing;
    }

    @Override
    public Optional<Borrowing> findById(Integer id) {
        return Optional.ofNullable(borrowings.get(id));
    }

    @Override
    public List<Borrowing> findAll() {
        return new ArrayList<>(borrowings.values());
    }

    @Override
    public List<Borrowing> findByUserId(Integer userId) {
        return borrowings.values().stream()
                .filter(borrowing -> Objects.equals(borrowing.getUserId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Borrowing> findByBookId(Integer bookId) {
        return borrowings.values().stream()
                .filter(borrowing -> Objects.equals(borrowing.getBookId(), bookId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Borrowing> findActiveByUserId(Integer userId) {
        return borrowings.values().stream()
                .filter(borrowing -> Objects.equals(borrowing.getUserId(), userId) &&
                        Borrowing.Status.ACTIVE.equals(borrowing.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Borrowing> findActiveByBookId(Integer bookId) {
        return borrowings.values().stream()
                .filter(borrowing -> Objects.equals(borrowing.getBookId(), bookId) &&
                        Borrowing.Status.ACTIVE.equals(borrowing.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Borrowing> findByStatus(Borrowing.Status status) {
        return borrowings.values().stream()
                .filter(borrowing -> Objects.equals(borrowing.getStatus(), status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Borrowing> findOverdueBorrowings() {
        LocalDate today = LocalDate.now();
        return borrowings.values().stream()
                .filter(borrowing -> Borrowing.Status.ACTIVE.equals(borrowing.getStatus()) &&
                        borrowing.getDueDate() != null &&
                        borrowing.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    @Override
    public List<Borrowing> findByDueDate(LocalDate dueDate) {
        return borrowings.values().stream()
                .filter(borrowing -> Objects.equals(borrowing.getDueDate(), dueDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Borrowing> findByBorrowDateBetween(LocalDate startDate, LocalDate endDate) {
        return borrowings.values().stream()
                .filter(borrowing -> {
                    LocalDate borrowDate = borrowing.getBorrowDate();
                    return borrowDate != null &&
                            !borrowDate.isBefore(startDate) &&
                            !borrowDate.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Borrowing update(Borrowing borrowing) {
        if (borrowing.getId() == null || !borrowings.containsKey(borrowing.getId())) {
            throw new IllegalArgumentException("Borrowing with ID " + borrowing.getId() + " does not exist");
        }
        borrowings.put(borrowing.getId(), borrowing);
        return borrowing;
    }

    @Override
    public void deleteById(Integer id) {
        borrowings.remove(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return borrowings.containsKey(id);
    }

    @Override
    public long count() {
        return borrowings.size();
    }

    @Override
    public long countActiveByUserId(Integer userId) {
        return borrowings.values().stream()
                .filter(borrowing -> Objects.equals(borrowing.getUserId(), userId) &&
                        Borrowing.Status.ACTIVE.equals(borrowing.getStatus()))
                .count();
    }
}
