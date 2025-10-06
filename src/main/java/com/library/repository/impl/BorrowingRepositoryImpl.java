package com.library.repository.impl;

import com.library.model.Book;
import com.library.model.Borrowing;
import com.library.model.User;
import com.library.repository.IBookRepository;
import com.library.repository.IBorrowingRepository;
import com.library.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class BorrowingRepositoryImpl implements IBorrowingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Borrowing save(Borrowing borrowing) {
        String sql = "INSERT INTO borrowings (book_id, user_id, borrow_date, due_date, status) " +
                     "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
            sql,
            borrowing.getBookId(),
            borrowing.getUserId(),
            borrowing.getBorrowDate(),
            borrowing.getDueDate(),
            borrowing.getStatus().toString()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        borrowing.setId(id);

        // Cập nhật trạng thái sách
        jdbcTemplate.update("UPDATE books SET available = false, borrowing_count = borrowing_count + 1  WHERE id = ?", borrowing.getBookId());

        return borrowing;
    }

    @Override
    public Optional<Borrowing> findById(Long id) {
        String sql = "SELECT * FROM borrowings WHERE id = ?";
        try {
            Borrowing borrowing = jdbcTemplate.queryForObject(sql, this::mapBorrowingRow, id);
            return Optional.ofNullable(borrowing);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Borrowing> findAll() {
        String sql = "SELECT * FROM borrowings ORDER BY id DESC";
        return jdbcTemplate.query(sql, this::mapBorrowingRow);
    }

    @Override
    public List<Borrowing> findByBookId(Long bookId) {
        String sql = "SELECT * FROM borrowings WHERE book_id = ? ORDER BY borrow_date DESC";
        return jdbcTemplate.query(sql, this::mapBorrowingRow, bookId);
    }

    @Override
    public List<Borrowing> findByUserId(Long userId) {
        String sql = "SELECT * FROM borrowings WHERE user_id = ? ORDER BY borrow_date DESC";
        return jdbcTemplate.query(sql, this::mapBorrowingRow, userId);
    }

    @Override
    public List<Borrowing> findActiveByBookId(Long bookId) {
        String sql = "SELECT * FROM borrowings WHERE book_id = ? AND status = 'ACTIVE' ORDER BY borrow_date DESC";
        return jdbcTemplate.query(sql, this::mapBorrowingRow, bookId);
    }

    @Override
    public List<Borrowing> findByStatus(Borrowing.BorrowStatus status) {
        String sql = "SELECT * FROM borrowings WHERE status = ? ORDER BY borrow_date DESC";
        return jdbcTemplate.query(sql, this::mapBorrowingRow, status.toString());
    }

    @Override
    public Borrowing update(Borrowing borrowing) {
        String sql = "UPDATE borrowings SET status = ?, return_date = ? WHERE id = ?";

        jdbcTemplate.update(
            sql,
            borrowing.getStatus().toString(),
            borrowing.getReturnDate(),
            borrowing.getId()
        );

        // Nếu sách được trả, cập nhật trạng thái sách
        if (borrowing.getStatus() == Borrowing.BorrowStatus.RETURNED) {
            jdbcTemplate.update("UPDATE books SET available = true WHERE id = ?", borrowing.getBookId());
        }

        return borrowing;
    }

    @Override
    public void deleteById(Long id) {
        // Trước khi xóa, có thể cần lấy thông tin mượn để cập nhật trạng thái sách
        Optional<Borrowing> borrowingOpt = findById(id);
        if (borrowingOpt.isPresent() && borrowingOpt.get().getStatus() == Borrowing.BorrowStatus.ACTIVE) {
            // Nếu đang mượn, cập nhật sách về available
            jdbcTemplate.update("UPDATE books SET available = true WHERE id = ?",
                               borrowingOpt.get().getBookId());
        }

        jdbcTemplate.update("DELETE FROM borrowings WHERE id = ?", id);
    }
    @Override
    public List<Borrowing> searchBorrowings(String status, Long userId, String dateFrom, String dateTo) {
        StringBuilder sql = new StringBuilder(
            "SELECT b.*  FROM borrowings b " +
            "JOIN users u ON b.user_id = u.id " +
            "JOIN books bk ON b.book_id = bk.id WHERE 1=1"
        );
        List<Object> params = new java.util.ArrayList<>();

        if (status != null && !status.isBlank()) {
            sql.append(" AND b.status = ?");
            params.add(status);
        }
        if (userId != null) {
            sql.append(" AND u.id = ?");
            params.add(userId);
        }
        if (dateFrom != null && !dateFrom.isBlank()) {
            sql.append(" AND b.borrow_date >= ?");
            params.add(dateFrom);
        }
        if (dateTo != null && !dateTo.isBlank()) {
            sql.append(" AND b.borrow_date <= ?");
            params.add(dateTo);
        }
        sql.append(" ORDER BY b.borrow_date DESC");
        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            Borrowing borrowing = mapBorrowingRow(rs, rowNum);
            return borrowing;
        }, params.toArray());
    }

    private Borrowing mapBorrowingRow(ResultSet rs, int rowNum) throws SQLException {
        Borrowing borrowing = new Borrowing();
        borrowing.setId(rs.getLong("id"));
        borrowing.setBookId(rs.getLong("book_id"));
        borrowing.setUserId(rs.getLong("user_id"));
        borrowing.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
        borrowing.setDueDate(rs.getDate("due_date").toLocalDate());

        if (rs.getDate("return_date") != null) {
            borrowing.setReturnDate(rs.getDate("return_date").toLocalDate());
        }

        borrowing.setStatus(Borrowing.BorrowStatus.valueOf(rs.getString("status")));
        borrowing.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        // Tải thêm thông tin sách và người dùng
        loadRelatedEntities(borrowing);

        return borrowing;
    }

    private void loadRelatedEntities(Borrowing borrowing) {
        // Tải thông tin sách
        bookRepository.findById(borrowing.getBookId())
            .ifPresent(borrowing::setBook);

        // Tải thông tin người dùng
        userRepository.findById(borrowing.getUserId())
            .ifPresent(borrowing::setUser);
    }

    @Override
    public Long currentBorrowings(){
        String sql = "SELECT COUNT(*) FROM borrowings WHERE status = 'ACTIVE'";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
    @Override
    public Long overDueCount(){
        String sql = "SELECT COUNT(*) FROM borrowings WHERE status = 'OVERDUE'";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
