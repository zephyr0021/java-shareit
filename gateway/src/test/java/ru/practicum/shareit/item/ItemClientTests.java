package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.item.dto.NewCommentRequestDto;
import ru.practicum.shareit.item.dto.NewItemRequestDto;
import ru.practicum.shareit.item.dto.UpdateItemRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemClientTests {
    @Autowired
    private ItemClient itemClient;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void getItems() {
        when(restTemplate.exchange(eq("/items"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        itemClient.getItems(1L);

        verify(restTemplate, times(1)).exchange(eq("/items"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void getItem() {
        when(restTemplate.exchange(eq("/items/1"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        itemClient.getItem(1L, 1L);

        verify(restTemplate, times(1)).exchange(eq("/items/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void searchItems() {
        when(restTemplate.exchange(eq("/items/search?text=test"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        itemClient.searchItems(1L, "test");

        verify(restTemplate, times(1)).exchange(eq("/items/search?text=test"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void createItem() {
        when(restTemplate.exchange(eq("/items"), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        itemClient.createItem(1L, new NewItemRequestDto());

        verify(restTemplate, times(1)).exchange(eq("/items"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void updateItem() {
        when(restTemplate.exchange(eq("/items/1"), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        itemClient.updateItem(1L, 1L, new UpdateItemRequestDto());

        verify(restTemplate, times(1)).exchange(eq("/items/1"), eq(HttpMethod.PATCH),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void setComment() {
        when(restTemplate.exchange(eq("/items/1/comment"), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        itemClient.setComment(1L, 1L, new NewCommentRequestDto());

        verify(restTemplate, times(1)).exchange(eq("/items/1/comment"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(Object.class));
    }
}
