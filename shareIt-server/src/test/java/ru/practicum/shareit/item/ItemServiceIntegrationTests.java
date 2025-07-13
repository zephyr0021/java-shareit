package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserValidationService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@DataJpaTest
@Import({ItemService.class, UserValidationService.class})
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ItemServiceIntegrationTests {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void createItem() {
        NewItemRequest request = new NewItemRequest("book", "bookDescription", true, 5L);

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

    }
}
