package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.dto.NewCommentRequestDto;
import ru.practicum.shareit.item.dto.NewItemRequestDto;
import ru.practicum.shareit.item.dto.UpdateItemRequestDto;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemClient itemClient;

    @Test
    void getItems() throws Exception {
        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(itemClient, Mockito.times(1)).getItems(1L);
    }

    @Test
    void getItem() throws Exception {
        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(itemClient, Mockito.times(1)).getItem(1L, 1L);
    }

    @Test
    void searchItems() throws Exception {
        mvc.perform(get("/items/search?text=blablabla")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(itemClient, Mockito.times(1)).searchItems(1L, "blablabla");
    }

    @Test
    void createItem() throws Exception {
        var item = new NewItemRequestDto("test", "test", Boolean.TRUE, 1L);
        when(itemClient.createItem(1L, item)).thenReturn(
                ResponseEntity.status(HttpStatus.CREATED).body(asJson(item))
        );

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isCreated());

        Mockito.verify(itemClient, Mockito.times(1)).createItem(1L, item);
    }

    @Test
    void updateItem() throws Exception {
        var item = new UpdateItemRequestDto("test", "test", false);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isOk());

        Mockito.verify(itemClient, Mockito.times(1)).updateItem(1L, 1L, item);
    }

    @Test
    void updateItemName() throws Exception {
        var item = new UpdateItemRequestDto("test", null, null);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isOk());

        Mockito.verify(itemClient, Mockito.times(1)).updateItem(1L, 1L, item);
    }

    @Test
    void updateItemDescription() throws Exception {
        var item = new UpdateItemRequestDto(null, "test", null);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isOk());

        Mockito.verify(itemClient, Mockito.times(1)).updateItem(1L, 1L, item);
    }

    @Test
    void updateItemAvailable() throws Exception {
        var item = new UpdateItemRequestDto(null, null, false);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isOk());

        Mockito.verify(itemClient, Mockito.times(1)).updateItem(1L, 1L, item);
    }

    @Test
    void setComment() throws Exception {
        var comment = new NewCommentRequestDto("test");
        when(itemClient.setComment(1L, 1L, comment)).thenReturn(
                ResponseEntity.status(HttpStatus.CREATED).body(asJson(comment))
        );

        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(comment)))
                .andExpect(status().isCreated());
        Mockito.verify(itemClient, Mockito.times(1)).setComment(1L, 1L, comment);
    }

    private String asJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
