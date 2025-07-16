package ru.practicum.shareit.item;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemModelTests {

    @Test
    void testEqualsAndHashCode() {
        Model<Item> model = Instancio.of(Item.class)
                .set(Select.field("id"), 1L)
                .toModel();
        Item item = Instancio.create(model);
        Item item2 = Instancio.create(model);

        assertEquals(item, item2);
        assertEquals(item.hashCode(), item2.hashCode());
    }
}
