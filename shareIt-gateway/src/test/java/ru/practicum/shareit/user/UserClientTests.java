package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.user.dto.NewUserRequestDto;
import ru.practicum.shareit.user.dto.UpdateUserRequestDto;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserClientTests {
    @Autowired
    private UserClient userClient;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    void getUsers() {
        when(restTemplate.exchange(eq("/users"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        userClient.getUsers();

        verify(restTemplate, times(1)).exchange(eq("/users"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));

    }

    @Test
    void getUser() {
        when(restTemplate.exchange(eq("/users/1"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        userClient.getUser(1L);

        verify(restTemplate, times(1)).exchange(eq("/users/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void createUser() {
        when(restTemplate.exchange(eq("/users"), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        userClient.createUser(new NewUserRequestDto());

        verify(restTemplate, times(1)).exchange(eq("/users"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void updateUser() {
        when(restTemplate.exchange(eq("/users/1"), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        userClient.updateUser(1L, new UpdateUserRequestDto());

        verify(restTemplate, times(1)).exchange(eq("/users/1"), eq(HttpMethod.PATCH),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void deleteUser() {
        when(restTemplate.exchange(eq("/users/1"), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        userClient.deleteUser(1L);

        verify(restTemplate, times(1)).exchange(eq("/users/1"), eq(HttpMethod.DELETE),
                any(HttpEntity.class), eq(Object.class));

    }

}
