package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemDto getItemById(long userId, long id) {
        isUserExistOrThrowNotFound(userId);
        return itemRepository.findById(id)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    public List<ItemDto> getAllItemsByUserId(long userId) {
        isUserExistOrThrowNotFound(userId);
        return itemRepository.findAllFromUserId(userId).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    public List<ItemDto> searchItems(long userId, String query) {
        isUserExistOrThrowNotFound(userId);
        return itemRepository.searchItems(query).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    public ItemDto createItem(NewItemRequest request, long userId) {
        isUserExistOrThrowNotFound(userId);
        Item item = ItemMapper.toItem(request);
        item.setOwnerId(userId);
        item = itemRepository.createItem(item);

        return ItemMapper.toItemDto(item);
    }

    public ItemDto updateItem(long userId, long itemId, UpdateItemRequest request) {
        getItemById(userId, itemId);
        Item newItem = itemRepository.findAllFromUserId(userId).stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .map(item -> ItemMapper.updateItemFields(item, request))
                .orElseThrow(() -> new AccessDeniedException("Cannot access to this item"));
        newItem = itemRepository.updateItem(newItem);

        return ItemMapper.toItemDto(newItem);

    }

    private void isUserExistOrThrowNotFound(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("X-Sharer-User-Id not found"));
    }
}
