package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public User createUser(User user) {
        user.setId(generateId());
        users.add(user);
        log.info("Created user: {}", user);
        return user;
    }

    @Override
    public User updateUser(User newUser) {
        User updatedUser = users.stream()
                .filter(user -> user.getId().equals(newUser.getId()))
                .findFirst()
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
        users.removeIf(user -> user.getId().equals(id));
        log.info("Deleted user: {}", id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public void clearData() {
        users.clear();
    }

    private Long generateId() {
        long lastId = users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
