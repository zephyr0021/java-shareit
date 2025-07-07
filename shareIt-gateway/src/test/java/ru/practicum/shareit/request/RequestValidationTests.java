package ru.practicum.shareit.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.NewItemRequestRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestController.class)
public class RequestValidationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestClient requestClient;

    @Test
    void createItemRequestWithEmptyDescription() throws Exception {
        var itemRequest = new NewItemRequestRequestDto("");
        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(itemRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation error"))
                .andExpect(jsonPath("$.message").value("description must not be blank or null or empty"));

        Mockito.verify(requestClient, Mockito.never()).createItemRequest(anyLong(), any());
    }

    @Test
    void createItemRequestWithoutDescription() throws Exception {
        var itemRequest = new NewItemRequestRequestDto();
        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(itemRequest)))
                .andExpect(status().isBadRequest());

        Mockito.verify(requestClient, Mockito.never()).createItemRequest(anyLong(), any());
    }

    @Test
    void createItemRequestWithWhiteSpaceDescription() throws Exception {
        var itemRequest = new NewItemRequestRequestDto(" ");
        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(itemRequest)))
                .andExpect(status().isBadRequest());

        Mockito.verify(requestClient, Mockito.never()).createItemRequest(anyLong(), any());
    }

    private String asJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
