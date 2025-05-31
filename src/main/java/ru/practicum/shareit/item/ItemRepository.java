package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<Item> findById(long id);

    Item createItem(Item item);

    List<Item> findAllFromUserId(long id);

    List<Item> searchItems(String query);

    Item updateItem(Item item);

    void clearData();
}
