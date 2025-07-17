package ru.practicum.shareit.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.NewUserRequestDto;
import ru.practicum.shareit.user.dto.UpdateUserRequestDto;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserClient userClient;

    @Test
    void getUsers() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
        Mockito.verify(userClient, Mockito.times(1)).getUsers();
    }

    @Test
    void getUser() throws Exception {
        mvc.perform(get("/users/1"))
                .andExpect(status().isOk());
        Mockito.verify(userClient, Mockito.times(1)).getUser(1L);
    }

    @Test
    void createUser() throws Exception {
        var user = new NewUserRequestDto("test", "test@mail.ru");
        when(userClient.createUser(user)).thenReturn(
                ResponseEntity.status(HttpStatus.CREATED).body(asJson(user))
        );
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isCreated());
        Mockito.verify(userClient, Mockito.times(1)).createUser(user);
    }

    @Test
    void updateUser() throws Exception {
        var user = new UpdateUserRequestDto("test_upd", "test@mail.ru");
        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isOk());
        Mockito.verify(userClient, Mockito.times(1)).updateUser(1L, user);
    }

    @Test
    void updateUserName() throws Exception {
        var user = new UpdateUserRequestDto("test_upd", null);
        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isOk());
        Mockito.verify(userClient, Mockito.times(1)).updateUser(1L, user);
    }

    @Test
    void updateUserEmail() throws Exception {
        var user = new UpdateUserRequestDto(null, "test@mail.ru");
        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(user)))
                .andExpect(status().isOk());
        Mockito.verify(userClient, Mockito.times(1)).updateUser(1L, user);
    }

    @Test
    void deleteUser() throws Exception {
        mvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
        Mockito.verify(userClient, Mockito.times(1)).deleteUser(1L);
    }


    private String asJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
