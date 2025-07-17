package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    private final String url = "/users";

    private UserDto user1;
    private UserDto user2;
    private UserDto user3;


    @BeforeEach
    void setUp() {
        user1 = new UserDto(1L, "John", "testJohn@mail.com");
        user2 = new UserDto(2L, "Jane", "testJane@mail.com");
        user3 = new UserDto(3L, "Joe", "testJoe@mail.com");
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserDto> users = List.of(user1, user2, user3);
        String expectedJson = mapper.writeValueAsString(users);

        when(userService.getAllUsers()).thenReturn(users);
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));

        Mockito.verify(userService, Mockito.times(1)).getAllUsers();
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user1);

        String expectedJson = mapper.writeValueAsString(user1);

        mvc.perform(get(url + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));

        Mockito.verify(userService, Mockito.times(1)).getUserById(1L);
    }

    @Test
    void createUser() throws Exception {
        NewUserRequest request = new NewUserRequest("Soul", "testSoul@mail.com");
        UserDto userDto = new UserDto(1L, "Soul", "testSoul@mail.com");
        String newUser = mapper.writeValueAsString(userDto);

        when(userService.createUser(request)).thenReturn(userDto);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(newUser, true));

        Mockito.verify(userService, Mockito.times(1)).createUser(request);
    }

    @Test
    void updateUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("Soul", "testSoul@mail.com");
        UserDto userDto = new UserDto(1L, "Soul", "testSoul@mail.com");
        String updatedUser = mapper.writeValueAsString(userDto);

        when(userService.updateUser(1L, request)).thenReturn(userDto);
        mvc.perform(patch(url + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedUser, true));

        Mockito.verify(userService, Mockito.times(1)).updateUser(1L, request);

    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mvc.perform(delete(url + "/1"))
                .andExpect(status().isOk());

        Mockito.verify(userService, Mockito.times(1)).deleteUser(1L);
    }

    @Test
    void getUnknownUser() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(new NotFoundException("User not found"));
        mvc.perform(get(url + "/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("not found"))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void createUserConflictData() throws Exception {
        NewUserRequest request = new NewUserRequest("John", "testJohn@mail.com");

        when(userService.createUser(request))
                .thenThrow(new ConflictException("User " + request.getEmail() + " already exists"));
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("conflict data"))
                .andExpect(jsonPath("$.message")
                        .value("User " + request.getEmail() + " already exists"));
    }

    @Test
    void updateUserConflictData() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("John", "testJohn@mail.com");
        when(userService.updateUser(1L, request))
                .thenThrow(new ConflictException("User " + request.getEmail() + " already exists"));
        mvc.perform(patch(url + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("conflict data"))
                .andExpect(jsonPath("$.message")
                        .value("User " + request.getEmail() + " already exists"));
    }

    @Test
    void updateUnknownUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("John", "testJohn@mail.com");
        when(userService.updateUser(1L, request))
                .thenThrow(new NotFoundException("User with id 1 not found"));
        mvc.perform(patch(url + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("not found"))
                .andExpect(jsonPath("$.message").value("User with id 1 not found"));
    }
}
