package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemModelTests {

    @Test
    void testEqualsAndHashCode() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("item1");
        item1.setDescription("item1");
        item1.setAvailable(true);
        item1.setOwner(new User());
        item1.setRequestId(2L);

        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("item2");
        item2.setDescription("item2");
        item2.setAvailable(false);
        item2.setOwner(new User());
        item2.setRequestId(3L);

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }
}
