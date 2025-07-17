package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@DataJpaTest
@Import(UserService.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceIntegrationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser() {
        NewUserRequest request = new NewUserRequest("Heisenberg", "MetKing@example.com");
        userService.createUser(request);

        Optional<User> user = userRepository.findById(5L);

        assertTrue(user.isPresent());
        assertEquals(request.getName(), user.get().getName());
        assertEquals(request.getEmail(), user.get().getEmail());
    }

    @Test
    void updateUser() {
        UpdateUserRequest request = new UpdateUserRequest("Heisenberg", "MetKing@example.com");
        userService.updateUser(1L, request);

        Optional<User> user = userRepository.findById(1L);

        assertTrue(user.isPresent());
        assertEquals(request.getName(), user.get().getName());
        assertEquals(request.getEmail(), user.get().getEmail());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1L);

        Optional<User> user = userRepository.findById(1L);

        assertTrue(user.isEmpty());
    }

}
