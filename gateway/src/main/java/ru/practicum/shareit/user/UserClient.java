package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.NewUserRequestDto;
import ru.practicum.shareit.user.dto.UpdateUserRequestDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> getUsers() {
        return get(API_PREFIX);
    }

    public ResponseEntity<Object> getUser(Long userId) {
        return get(API_PREFIX + "/" + userId);
    }

    public ResponseEntity<Object> createUser(NewUserRequestDto newUserRequestDto) {
        return post(API_PREFIX, newUserRequestDto);
    }

    public ResponseEntity<Object> updateUser(Long userId, UpdateUserRequestDto updateUserRequestDto) {
        return patch(API_PREFIX + "/" + userId, updateUserRequestDto);
    }

    public ResponseEntity<Object> deleteUser(Long userId) {
        return delete(API_PREFIX + "/" + userId);
    }
}
