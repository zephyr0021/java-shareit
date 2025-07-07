package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.NewCommentRequestDto;
import ru.practicum.shareit.item.dto.NewItemRequestDto;
import ru.practicum.shareit.item.dto.UpdateItemRequestDto;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ItemController.class)
public class ItemValidationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemClient itemClient;

    @Test
    void createItemWithEmptyName() throws Exception {
        var item = new NewItemRequestDto("", "test", true, null);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).createItem(anyLong(), any());
    }

    @Test
    void createItemWithoutName() throws Exception {
        var item = new NewItemRequestDto(null, "test", true, null);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).createItem(anyLong(), any());
    }

    @Test
    void createItemWithWhiteSpaceName() throws Exception {
        var item = new NewItemRequestDto(" ", "test", true, null);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).createItem(anyLong(), any());
    }

    @Test
    void createItemWithEmptyDescription() throws Exception {
        var item = new NewItemRequestDto("test", "", true, null);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).createItem(anyLong(), any());
    }

    @Test
    void createItemWithoutDescription() throws Exception {
        var item = new NewItemRequestDto("test", null, true, null);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).createItem(anyLong(), any());
    }

    @Test
    void createItemWithWhiteSpaceDescription() throws Exception {
        var item = new NewItemRequestDto("test", " ", true, null);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).createItem(anyLong(), any());
    }

    @Test
    void createItemWithoutAvailable() throws Exception {
        var item = new NewItemRequestDto("test", "test", null, null);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).createItem(anyLong(), any());
    }

    @Test
    void updateItemWithEmptyName() throws Exception {
        var item = new UpdateItemRequestDto("", "test", true);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).updateItem(anyLong(), anyLong(), any());
    }

    @Test
    void updateItemWithWhiteSpaceName() throws Exception {
        var item = new UpdateItemRequestDto(" ", "test", true);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).updateItem(anyLong(), anyLong(), any());
    }

    @Test
    void updateItemWithEmptyDescription() throws Exception {
        var item = new UpdateItemRequestDto("", "test", true);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).updateItem(anyLong(), anyLong(), any());
    }

    @Test
    void updateItemWithWhiteSpaceDescription() throws Exception {
        var item = new UpdateItemRequestDto("test", " ", true);
        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(item)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).updateItem(anyLong(), anyLong(), any());
    }

    @Test
    void setCommentWithEmptyText() throws Exception {
        var comment = new NewCommentRequestDto("");
        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(comment)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).setComment(anyLong(), anyLong(), any());
    }

    @Test
    void setCommentWithoutText() throws Exception {
        var comment = new NewCommentRequestDto(null);
        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(comment)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).setComment(anyLong(), anyLong(), any());
    }

    @Test
    void setCommentWithWhiteSpaceText() throws Exception {
        var comment = new NewCommentRequestDto(" ");
        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(comment)))
                .andExpect(status().isBadRequest());
        Mockito.verify(itemClient, Mockito.never()).setComment(anyLong(), anyLong(), any());
    }

    private String asJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
