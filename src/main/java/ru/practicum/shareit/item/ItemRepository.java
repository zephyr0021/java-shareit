package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.Optional;

public interface ItemRepository {
    Optional<Item> findById(long id);

    Item createItem(Item item);
}
