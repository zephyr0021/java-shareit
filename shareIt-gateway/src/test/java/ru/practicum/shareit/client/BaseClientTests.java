package ru.practicum.shareit.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BaseClientTests {

    private BaseClient baseClient;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        baseClient = new BaseClient(restTemplate);
    }

    @Test
    void handleErrorServerResponse() {
        ResponseEntity<Object> expectedResponse = ResponseEntity.internalServerError().body("internal server error");
        when(restTemplate.exchange(
                eq("/internal-server-error"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class)
        )).thenReturn(expectedResponse);

        var response = baseClient.get("/internal-server-error");
        assertEquals(response.getStatusCode(), expectedResponse.getStatusCode());
        assertEquals(response.getBody(), expectedResponse.getBody());

    }

    @Test
    void shouldReturnErrorResponseWhenHttpStatusCodeExceptionThrown() {
        String errorBody = "{\"error\":\"not found\"}";
        HttpStatus status = HttpStatus.NOT_FOUND;

        when(restTemplate.exchange(
                eq("/some-path"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)
        )).thenThrow(HttpClientErrorException.create(
                status, "Not Found",
                HttpHeaders.EMPTY, errorBody.getBytes(), null
        ));

        // when
        ResponseEntity<Object> response = baseClient.get("/some-path", 1L);

        // then
        assertEquals(status, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(errorBody, response.getBody());
    }
}