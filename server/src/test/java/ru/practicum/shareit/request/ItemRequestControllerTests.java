package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemRequestController.class)
public class ItemRequestControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemRequestService requestService;

    private final String url = "/requests";

    private ItemRequestDto request;
    private ItemRequestDto request2;
    private ItemRequestDto request3;
    private NewItemRequestRequest newRequest;

    @BeforeEach
    void setup() {
        request = Instancio.create(ItemRequestDto.class);
        request2 = Instancio.create(ItemRequestDto.class);
        request3 = Instancio.create(ItemRequestDto.class);
        newRequest = Instancio.create(NewItemRequestRequest.class);
    }

    @Test
    void getItemRequest() throws Exception {
        String expectedJson = mapper.writeValueAsString(request);
        when(requestService.getItemRequest(1L, request.getId())).thenReturn(request);
        mvc.perform(get(url + "/" + request.getId())
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }

    @Test
    void getUserItemRequests() throws Exception {
        List<ItemRequestDto> requests = List.of(request2, request3);
        String expectedJson = mapper.writeValueAsString(requests);
        when(requestService.getUserItemRequests(1L)).thenReturn(requests);
        mvc.perform(get(url)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getAllItemRequests() throws Exception {
        List<ItemRequestDto> requests = List.of(request, request2, request3);
        String expectedJson = mapper.writeValueAsString(requests);
        when(requestService.getAllItemRequests(1L)).thenReturn(requests);
        mvc.perform(get(url + "/all")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void createItemRequest() throws Exception {
        String expectedJson = mapper.writeValueAsString(request);
        when(requestService.createItemRequest(eq(1L), any(NewItemRequestRequest.class))).thenReturn(request);
        mvc.perform(post(url)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

}
