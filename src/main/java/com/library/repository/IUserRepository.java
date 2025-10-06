package com.library.repository;

import com.library.dto.UserReportDTO;
import com.library.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    User update(User user);
    void deleteById(Long id);
    List<User> searchUsers(String query, String role, String status);
    Long activeUsersCount();
    List<UserReportDTO> generateUserReport();
}
