package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.item.dto.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.OffsetDateTime;
import java.util.List;

@WebMvcTest(ItemController.class)
public class ItemControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemService itemService;

    private final String url = "/items";

    private ItemWithBookingsAndCommentsDto item;
    private ItemWithBookingsAndCommentsDto item2;
    private ItemDto item4;
    private ItemDto item5;

    @BeforeEach
    void setUp() {
        item = Instancio.create(ItemWithBookingsAndCommentsDto.class);
        item2 = Instancio.create(ItemWithBookingsAndCommentsDto.class);
        item4 = Instancio.create(ItemDto.class);
        item5 = Instancio.create(ItemDto.class);
    }

    @Test
    void getItem() throws Exception {
        String expectedJson = mapper.writeValueAsString(item);
        when(itemService.getItemById(1L, item.getId())).thenReturn(item);
        mvc.perform(get(url + "/" + item.getId())
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Mockito.verify(itemService, Mockito.times(1)).getItemById(1L, item.getId());

    }

    @Test
    void getUserItems() throws Exception {
        String expectedJson = mapper.writeValueAsString(List.of(item, item2));
        when(itemService.getAllItemsByUserId(1L)).thenReturn(List.of(item, item2));
        mvc.perform(get(url)
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Mockito.verify(itemService, Mockito.times(1)).getAllItemsByUserId(1L);
    }

    @Test
    void searchItems() throws Exception {
        String expectedJson = mapper.writeValueAsString(List.of(item4, item5));
        when(itemService.searchItems(1L, "test")).thenReturn(List.of(item4, item5));
        mvc.perform(get(url + "/search?text=test")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Mockito.verify(itemService, Mockito.times(1)).searchItems(1L, "test");
    }

    @Test
    void createItem() throws Exception {
        NewItemRequest request = new NewItemRequest("book", "very intresting book", true, 1L);
        ItemDto item = new ItemDto(1L, "book", "very intresting book", true);
        String expectedJson = mapper.writeValueAsString(item);

        when(itemService.createItem(request, 1L)).thenReturn(item);

        mvc.perform(post(url)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        Mockito.verify(itemService, Mockito.times(1)).createItem(request, 1L);
    }

    @Test
    void updateItem() throws Exception {
        UpdateItemRequest request = new UpdateItemRequest("book_upd", "descr_upd", false);
        ItemDto item = new ItemDto(1L, "book_upd", "descr_upd", false);
        String expectedJson = mapper.writeValueAsString(item);

        when(itemService.updateItem(1L, 1L, request)).thenReturn(item);

        mvc.perform(patch(url + "/" + item.getId())
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Mockito.verify(itemService, Mockito.times(1)).updateItem(1L, 1L, request);
    }

    @Test
    void createComment() throws Exception {
        NewCommentRequest request = new NewCommentRequest("test_comment");
        CommentDto comment = new CommentDto(1L, "test_comment", "author", OffsetDateTime.now());
        String expectedJson = mapper.writeValueAsString(comment);

        when(itemService.setComment(1L, 1L, request)).thenReturn(comment);

        mvc.perform(post(url + "/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        Mockito.verify(itemService, Mockito.times(1)).setComment(1L, 1L, request);
    }

    @Test
    void notUpdateItem() throws Exception {
        UpdateItemRequest request = new UpdateItemRequest("book_upd", "descr_upd", false);

        when(itemService.updateItem(1L, 1L, request)).thenThrow(new AccessDeniedException("Cannot access to this item"));

        mvc.perform(patch(url + "/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("access denied"))
                .andExpect(jsonPath("$.message").value("Cannot access to this item"));
    }
}
