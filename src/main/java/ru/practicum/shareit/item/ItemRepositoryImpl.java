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

    private long generateId() {
        long lastId = items.stream()
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
