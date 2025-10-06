package com.library.repository.impl;

import com.library.model.User;
import com.library.dto.UserReportDTO;
import com.library.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (username, password, full_name, email, profile_image_url ,phone, role, status) " +
                     "VALUES ( ?, ?, ?,?, ?, ?, ?,?)";

        jdbcTemplate.update(
            sql,
            user.getUsername(),
            user.getPassword(),
            user.getFullName(),
            user.getEmail(),
            user.getProfileImageUrl(),
            user.getPhone(),
            user.getRole().toString(),
            user.getStatus().toString()

        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        user.setId(id);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users ORDER BY id DESC";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET username=?, full_name=?, email=?, phone=?, role=?, status=?, profile_image_url=? WHERE id=?";

        jdbcTemplate.update(
            sql,
            user.getUsername(),
            user.getFullName(),
            user.getEmail(),
            user.getPhone(),
            user.getRole().toString(),
            user.getStatus().toString(),
            user.getProfileImageUrl(),
            user.getId()
        );

        return user;
    }

    public void updatePassword(Long userId, String newPassword) {
        String sql = "UPDATE users SET password=? WHERE id=?";
        jdbcTemplate.update(sql, newPassword, userId);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }
    @Override
    public Long activeUsersCount() {
        String sql = "SELECT COUNT(*) FROM users WHERE status = 'ACTIVE'";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
@Override
    public List<User> searchUsers(String query, String role, String status) {
        if (query == null) query = "";
        String like = "%" + query + "%";
        String sql = "SELECT * FROM users WHERE (full_name LIKE ? OR username LIKE ? OR email LIKE ? OR phone LIKE ?)";
        List<Object> params = new ArrayList<>(Arrays.asList(like, like, like, like));

        if (role != null && !role.isBlank()) {
            sql += " AND role = ?";
            params.add(role);
        }
        if (status != null && !status.isBlank()) {
            sql += " AND status = ?";
            params.add(status);
        }

        return jdbcTemplate.query(sql, new UserRowMapper(), params.toArray());
    }

    private static class UserRowMapper extends BeanPropertyRowMapper<User> {
        public UserRowMapper() {
            super(User.class);
        }

        @Override
        public User mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));

            String roleStr = rs.getString("role");
            user.setRole(User.UserRole.valueOf(roleStr));

            String statusStr = rs.getString("status");
            user.setStatus(User.UserStatus.valueOf(statusStr));

            user.setProfileImageUrl(rs.getString("profile_image_url"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

            return user;
        }
    }
    @Override
    public List<UserReportDTO> generateUserReport() {
        String sql = "SELECT " +
                "u.id, " +
                "u.full_name AS user_name, " +
                "u.email, " +
                "COUNT(b.id) AS total_borrowings, " +
                "SUM(CASE WHEN b.return_date IS NULL THEN 1 ELSE 0 END) AS current_borrowings, " +
                "SUM(CASE WHEN b.return_date IS NULL AND b.due_date < NOW() THEN 1 ELSE 0 END) AS overdue_items " +
                "FROM users u " +
                "LEFT JOIN borrowings b ON u.id = b.user_id " +
                "GROUP BY u.id, u.full_name, u.email " +
                "ORDER BY total_borrowings DESC " +
                "LIMIT 5";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserReportDTO(
                rs.getLong("id"),
                rs.getString("user_name"),
                rs.getString("email"),
                rs.getInt("total_borrowings"),
                rs.getInt("current_borrowings"),
                rs.getInt("overdue_items")
        ));
    }

}
