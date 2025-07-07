package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.NewItemRequestRequestDto;

@Service
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> getItemRequest(Long userId, Long id) {
        return get(API_PREFIX + "/" + id, userId);
    }

    public ResponseEntity<Object> getUserItemRequests(Long userId) {
        return get(API_PREFIX, userId);
    }

    public ResponseEntity<Object> getAllItemRequests(Long userId) {
        return get(API_PREFIX + "/all", userId);
    }

    public ResponseEntity<Object> createItemRequest(Long userId, NewItemRequestRequestDto request) {
        return post(API_PREFIX, userId, request);
    }
}
