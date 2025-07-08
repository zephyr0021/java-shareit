package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceNegativeTests {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Soul");
        user.setEmail("soulgoodman@test.com");
    }

    @Test
    void getUnknownUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(555L));
    }

    @Test
    void createUserWithSameEmail() {
        NewUserRequest newUserRequest = new NewUserRequest("John", "soulgoodman@test.com");
        when(userRepository.findByEmail("soulgoodman@test.com")).thenReturn(List.of(user));

        assertThrows(ConflictException.class, () -> userService.createUser(newUserRequest));
    }

    @Test
    void updateUserWithSameEmail() {
        UpdateUserRequest request = new UpdateUserRequest("John", "soulgoodman@test.com");
        when(userRepository.findByEmailAndIdNot("soulgoodman@test.com", 2L)).thenReturn(List.of(user));

        assertThrows(ConflictException.class, () -> userService.updateUser(2L, request));

    }

    @Test
    void updateUnknownUser() {
        UpdateUserRequest request = new UpdateUserRequest("John", "soulgoodman@test.com");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(1L, request));
    }



}
