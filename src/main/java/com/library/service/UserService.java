package com.library.service;

import com.library.model.User;
import com.library.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final String UPLOAD_DIR = "/home/doanlinh/Downloads/Library-Management/uploads";

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /** 
     * Tạo người dùng mới (có xử lý upload ảnh, mã hóa mật khẩu, kiểm tra trùng username)
     */
    public User createUser(User user, MultipartFile profileImage) throws Exception {
        validateUsername(user);
        handleProfileImage(user, profileImage);
        encodePasswordIfNeeded(user);
        return userRepository.save(user);
    }

    /**
     * Cập nhật người dùng
     */
    public User updateUser(Long id, User user, MultipartFile profileImage) throws Exception {
        user.setId(id);
        handleProfileImage(user, profileImage);
        encodePasswordIfNeeded(user);
        return userRepository.update(user);
    }

    private void validateUsername(User user) {
        userRepository.findByUsername(user.getUsername())
            .filter(existing -> !existing.getId().equals(user.getId()))
            .ifPresent(u -> { throw new RuntimeException("Username already exists"); });
    }

    private void handleProfileImage(User user, MultipartFile file) throws Exception {
        if (file != null && !file.isEmpty()) {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(dir, fileName);
            file.transferTo(dest);
            user.setProfileImageUrl("/uploads/" + fileName);
        }
    }

    private void encodePasswordIfNeeded(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            if (!user.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            userRepository.findById(user.getId())
                    .ifPresent(existing -> user.setPassword(existing.getPassword()));
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> searchUsers(String query, String role, String status) {
        return userRepository.searchUsers(query, role, status);
    }

    public List<User> getActiveUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getStatus() == User.UserStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}

