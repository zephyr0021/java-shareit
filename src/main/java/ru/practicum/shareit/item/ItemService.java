package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemDto getItemById(long id) {
        return itemRepository.findById(id)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    public ItemDto createItem(NewItemRequest request, long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("X-Sharer-User-Id not found"));
        Item item = ItemMapper.toItem(request);
        item.setOwnerId(userId);
        item = itemRepository.createItem(item);

        return ItemMapper.toItemDto(item);
    }
}
