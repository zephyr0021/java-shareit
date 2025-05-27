package ru.practicum.shareit.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(long id);

    boolean existsByEmail(String email);
}
