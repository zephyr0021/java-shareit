package ru.practicum.shareit.user;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Collection<User> findAll();

    Optional<User> findById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    boolean existsByEmail(User user);

    void clearData();
}
