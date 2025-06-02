package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public Item createItem(Item item) {
        Long itemId = generateId();
        item.setId(itemId);
        items.put(itemId, item);
        log.info("Created new item: {}", item);
        return item;
    }

    @Override
    public List<Item> findAllFromUserId(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwnerId().equals(userId))
                .toList();
    }

    @Override
    public List<Item> searchItems(String query) {
        String loweredQuery = query.toLowerCase();
        if (query.isBlank()) return List.of();
        return items.values().stream()
                .filter(item -> (item.getName().toLowerCase().contains(loweredQuery) ||
                        item.getDescription().toLowerCase().contains(loweredQuery)) &&
                        item.getAvailable())
                .toList();
    }

    @Override
    public Item updateItem(Item newItem) {
        Item updatedItem = findById(newItem.getId())
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

    private Long generateId() {
        long lastId = items.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
