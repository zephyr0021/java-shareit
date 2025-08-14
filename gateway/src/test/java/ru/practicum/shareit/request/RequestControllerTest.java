package ru.practicum.shareit.request;

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
import ru.practicum.shareit.request.dto.NewItemRequestRequestDto;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestController.class)
public class RequestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestClient requestClient;

    @Test
    void getItemRequest() throws Exception {
        mvc.perform(get("/requests/1")
                .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(requestClient, Mockito.times(1)).getItemRequest(1L, 1L);
    }

    @Test
    void getUserItemRequests() throws Exception {
        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(requestClient, Mockito.times(1)).getUserItemRequests(1L);
    }

    @Test
    void getAllItemRequests() throws Exception {
        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(requestClient, Mockito.times(1)).getAllItemRequests(1L);
    }

    @Test
    void createItemRequest() throws Exception {
        var itemRequest = new NewItemRequestRequestDto("test");
        when(requestClient.createItemRequest(1L, itemRequest)).thenReturn(
                ResponseEntity.status(HttpStatus.CREATED).body(asJson(itemRequest))
        );

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(itemRequest)))
                .andExpect(status().isCreated());

        Mockito.verify(requestClient, Mockito.times(1)).createItemRequest(1L, itemRequest);
    }

    private String asJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
