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
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User user;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Soul");
        user.setEmail("soulgoodman@test.com");

        user2 = new User();
        user2.setId(2L);
        user2.setName("Mike");
        user2.setEmail("mike@test.com");

        user3 = new User();
        user3.setId(3L);
        user3.setName("Hiezenberg");
        user3.setEmail("metking@test.com");
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user, user2, user3));

        List<UserDto> users = userService.getAllUsers();

        assertEquals(3, users.size());
        assertEquals(1L, users.get(0).getId());
        assertEquals("Mike", users.get(1).getName());
        assertEquals("metking@test.com", users.get(2).getEmail());
    }

    @Test
    void getUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDto user = userService.getUserById(1L);

        assertEquals(1L, user.getId());
        assertEquals("Soul", user.getName());
        assertEquals("soulgoodman@test.com", user.getEmail());
    }

    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        NewUserRequest newUserRequest = new NewUserRequest("Soul", "soulgoodman@test.com");
        UserDto userDto = userService.createUser(newUserRequest);

        assertEquals(1L, userDto.getId());
        assertEquals("Soul", userDto.getName());
        assertEquals("soulgoodman@test.com", userDto.getEmail());
    }

    @Test
    void updateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UpdateUserRequest request = new UpdateUserRequest("Soul", "soulgoodman@test.com");
        UserDto userDto = userService.updateUser(1L, request);

        assertEquals(1L, userDto.getId());
        assertEquals("Soul", userDto.getName());
        assertEquals("soulgoodman@test.com", userDto.getEmail());
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
