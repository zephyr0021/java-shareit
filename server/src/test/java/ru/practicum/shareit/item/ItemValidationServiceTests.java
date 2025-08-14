package ru.practicum.shareit.item;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemValidationServiceTests {
    @Mock
    private ItemRepository repository;

    @InjectMocks
    private ItemValidationService service;

    private Item item = Instancio.create(Item.class);

    @Test
    void isItemNotExistThrowNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.isItemExistOrThrowNotFound(anyLong()));
    }

    @Test
    void isItemExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(item));
        Item itemFind = service.isItemExistOrThrowNotFound(anyLong());
        assertEquals(item, itemFind);
    }
}
