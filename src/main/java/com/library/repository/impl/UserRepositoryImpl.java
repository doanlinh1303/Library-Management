package com.library.repository.impl;

import com.library.model.User;
import com.library.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> findByRole(User.Role role) {
        return users.values().stream()
                .filter(user -> Objects.equals(user.getRole(), role))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByStatus(User.Status status) {
        return users.values().stream()
                .filter(user -> Objects.equals(user.getStatus(), status))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findActiveUsers() {
        return findByStatus(User.Status.ACTIVE);
    }

    @Override
    public List<User> findByFullNameContainingIgnoreCase(String name) {
        return users.values().stream()
                .filter(user -> user.getFullName() != null &&
                        user.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    @Override
    public User update(User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " does not exist");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        users.remove(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return users.containsKey(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.values().stream()
                .anyMatch(user -> Objects.equals(user.getUsername(), username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(user -> Objects.equals(user.getEmail(), email));
    }

    @Override
    public long count() {
        return users.size();
    }
}
