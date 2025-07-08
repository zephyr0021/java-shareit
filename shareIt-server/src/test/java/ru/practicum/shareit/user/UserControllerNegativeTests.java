package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerNegativeTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    private final String url = "/users";

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

        when(userService.createUser(request)).
                thenThrow(new ConflictException("User " + request.getEmail() + " already exists"));
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
