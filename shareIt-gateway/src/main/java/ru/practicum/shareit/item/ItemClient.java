package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.NewCommentRequestDto;
import ru.practicum.shareit.item.dto.NewItemRequestDto;
import ru.practicum.shareit.item.dto.UpdateItemRequestDto;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> getItem(Long userId, Long id) {
        return get(API_PREFIX + "/" + id, userId);
    }

    public ResponseEntity<Object> getItems(Long userId) {
        return get(API_PREFIX, userId);
    }

    public ResponseEntity<Object> searchItems(Long userId, String query) {
        return get(API_PREFIX + "/search?text=" + query, userId);
    }

    public ResponseEntity<Object> createItem(Long userId, NewItemRequestDto request) {
        return post(API_PREFIX, userId, request);
    }

    public ResponseEntity<Object> updateItem(Long userId, Long id, UpdateItemRequestDto request) {
        return patch(API_PREFIX + "/" + id, userId, request);
    }

    public ResponseEntity<Object> setComment(Long userId, Long itemId, NewCommentRequestDto request) {
        return post(API_PREFIX + "/" + itemId + "/comment", userId, request);
    }
}
