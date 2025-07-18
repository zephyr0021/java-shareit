package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ServerException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserValidationService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DataJpaTest
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Import({ItemService.class, UserValidationService.class})
public class ItemServiceIntegrationTests {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void getAllItemsUser() {
        List<ItemWithBookingsAndCommentsDto> items = itemService.getAllItemsByUserId(4L);
        assertEquals(4, items.size());
        assertEquals(3, items.get(2).getLastBooking().getId());
    }

    @Test
    void createItem() {
        NewItemRequest request = new NewItemRequest("book", "bookDescription", true, 4L);

        itemService.createItem(request, 1L);

        Optional<Item> item = itemRepository.findById(7L);
        List<ItemShort> userItems = itemRepository.findItemsByOwnerId(1L);

        assertTrue(item.isPresent());
        assertEquals(request.getName(), item.get().getName());
        assertEquals(request.getDescription(), item.get().getDescription());
        assertEquals(request.getAvailable(), item.get().getAvailable());
        assertEquals(request.getRequestId(), item.get().getRequestId());
        assertEquals(1, userItems.size());
        assertEquals(userItems.getFirst().getId(), item.get().getId());
    }

    @Test
    void updateItem() {
        UpdateItemRequest request = new UpdateItemRequest("chair_upd", "simple chair_upd", true);

        itemService.updateItem(2L, 1L, request);

        Optional<Item> item = itemRepository.findById(1L);

        assertTrue(item.isPresent());
        assertEquals(request.getName(), item.get().getName());
        assertEquals(request.getDescription(), item.get().getDescription());
        assertEquals(request.getAvailable(), item.get().getAvailable());
    }

    @Test
    void searchItems() {
        List<ItemDto> items = itemService.searchItems(1L, "search");
        assertEquals(2, items.size());
        assertEquals(4L, items.get(0).getId());
        assertEquals(6L, items.get(1).getId());
    }

    @Test
    void setComment() {
        NewCommentRequest request = new NewCommentRequest("testComment");

        CommentDto comment = itemService.setComment(1L, 1L, request);
        assertEquals(1L, comment.getId());
        assertEquals(request.getText(), comment.getText());
        assertEquals("Alice", comment.getAuthorName());

        List<Comment> comments = commentRepository.findAllByItemId(1L);
        assertEquals(1, comments.size());
        assertEquals(1L, comments.getFirst().getId());
        assertEquals(request.getText(), comment.getText());
        assertEquals("Alice", comment.getAuthorName());
        assertEquals(1L, comments.getFirst().getItem().getId());
    }

    @Test
    void createItemUnknownUser() {
        NewItemRequest request = new NewItemRequest("book", "bookDescription", true, 5L);
        assertThrows(NotFoundException.class, () -> itemService.createItem(request, 25L));
    }

    @Test
    void notCreateComment() {
        NewCommentRequest request = new NewCommentRequest("testComment");
        assertThrows(ServerException.class, () -> itemService.setComment(3L, 2L, request));
    }
}
