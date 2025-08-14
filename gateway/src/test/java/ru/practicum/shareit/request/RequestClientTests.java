package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.request.dto.NewItemRequestRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RequestClientTests {
    @Autowired
    private RequestClient requestClient;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void getItemRequest() {
        when(restTemplate.exchange(eq("/requests/1"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        requestClient.getItemRequest(1L, 1L);

        verify(restTemplate, times(1)).exchange(eq("/requests/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void getUserItemRequests() {
        when(restTemplate.exchange(eq("/requests"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        requestClient.getUserItemRequests(1L);

        verify(restTemplate, times(1)).exchange(eq("/requests"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void getAllItemRequests() {
        when(restTemplate.exchange(eq("/requests/all"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        requestClient.getAllItemRequests(1L);

        verify(restTemplate, times(1)).exchange(eq("/requests/all"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void createItemRequest() {
        when(restTemplate.exchange(eq("/requests"), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        requestClient.createItemRequest(1L, new NewItemRequestRequestDto());

        verify(restTemplate, times(1)).exchange(eq("/requests"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(Object.class));
    }
}
