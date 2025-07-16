package ru.practicum.shareit.request;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.model.ItemRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemRequestModelTests {

    @Test
    void testEqualsAndHashCode() {
        Model<ItemRequest> model = Instancio.of(ItemRequest.class)
                .set(Select.field("id"), 1L)
                .toModel();
        ItemRequest request = Instancio.create(model);
        ItemRequest request2 = Instancio.create(model);

        assertEquals(request, request2);
        assertEquals(request.hashCode(), request2.hashCode());

    }
}
