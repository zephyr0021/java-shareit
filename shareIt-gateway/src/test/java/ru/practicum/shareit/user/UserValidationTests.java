package ru.practicum.shareit.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.NewUserRequestDto;
import ru.practicum.shareit.user.dto.UpdateUserRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserValidationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserClient userClient;

    @Test
    void createUserWithEmptyName() throws Exception {
        var user = new NewUserRequestDto("", "test@mail.ru");
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).createUser(user);
    }

    @Test
    void createUserWithoutName() throws Exception {
        var user = new NewUserRequestDto(null, "test@mail.ru");
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).createUser(user);
    }

    @Test
    void createUserWithWhiteSpaceName() throws Exception {
        var user = new NewUserRequestDto(" ", "test@mail.ru");
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).createUser(user);
    }

    @Test
    void createUserWithInvalidEmail() throws Exception {
        var user = new NewUserRequestDto("test", "test@");
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).createUser(user);
    }

    @Test
    void createUserWithoutEmail() throws Exception {
        var user = new NewUserRequestDto("test", null);
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).createUser(user);
    }

    @Test
    void createUserWithWhiteSpaceEmail() throws Exception {
        var user = new NewUserRequestDto("test", " ");
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).createUser(user);
    }

    @Test
    void createUserWithEmptyEmail() throws Exception {
        var user = new NewUserRequestDto("test", "");
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).createUser(user);
    }

    @Test
    void updateUserNameWithEmptyName() throws Exception {
        var user = new UpdateUserRequestDto("", null);
        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).updateUser(1L, user);
    }
    @Test
    void updateUserNameWithWhiteSpaceName() throws Exception {
        var user = new UpdateUserRequestDto(" ", null);
        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).updateUser(1L, user);
    }

    @Test
    void updateUserEmailWithEmptyEmail() throws Exception {
        var user = new UpdateUserRequestDto(null, "");
        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).updateUser(1L, user);
    }

    @Test
    void updateUserEmailWithWhiteSpaceEmail() throws Exception {
        var user = new UpdateUserRequestDto(null, " ");
        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).updateUser(1L, user);
    }

    @Test
    void updateUserEmailWithInvalidEmail() throws Exception {
        var user = new UpdateUserRequestDto(null, "test@");
        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isBadRequest());
        Mockito.verify(userClient, Mockito.never()).updateUser(1L, user);
    }


    private String asJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
