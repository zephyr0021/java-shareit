package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemValidationService {
    private final ItemRepository itemRepository;

    public Item isItemExistOrThrowNotFound(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
    }
}