package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    List<Item> items = new ArrayList<>();

    @Override
    public Optional<Item> findById(long id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item createItem(Item item) {
        item.setId(generateId());
        items.add(item);
        log.info("Created new item: {}", item);
        return item;
    }

    @Override
    public List<Item> findAllFromUserId(long userId) {
        return items.stream()
                .filter(item -> item.getOwnerId().equals(userId))
                .toList();
    }

    @Override
    public List<Item> searchItems(String query) {
        String loweredQuery = query.toLowerCase();
        if (query.isEmpty()) return List.of();
        return items.stream()
                .filter(item -> (item.getName().toLowerCase().contains(loweredQuery) ||
                        item.getDescription().toLowerCase().contains(loweredQuery)) &&
                        item.getAvailable())
                .toList();
    }

    @Override
    public Item updateItem(Item newItem) {
        Item updatedItem = items.stream()
                .filter(item -> item.getId().equals(newItem.getId()))
                .findFirst()
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setDescription(newItem.getDescription());
                    item.setAvailable(newItem.getAvailable());
                    return item;
                }).orElse(null);
        log.info("Updated item: {}", updatedItem);
        return updatedItem;
    }

    @Override
    public void clearData() {
        items.clear();
    }

    private long generateId() {
        long lastId = items.stream()
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
