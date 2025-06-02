package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User createUser(User user) {
        Long userId = generateId();
        user.setId(userId);
        users.put(userId, user);
        log.info("Created user: {}", user);
        return user;
    }

    @Override
    public User updateUser(User newUser) {
        User updatedUser = findById(newUser.getId())
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return user;
                })
                .orElse(null);
        log.info("Updated user: {}", updatedUser);
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
        log.info("Deleted user: {}", id);
    }

    @Override
    public boolean existsByEmail(User user) {
        return users.values().stream()
                .filter(u -> !Objects.equals(u.getId(), user.getId()))
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
    }

    @Override
    public void clearData() {
        users.clear();
    }

    private Long generateId() {
        long lastId = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
