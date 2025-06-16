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
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemDto getItemById(Long userId, Long id) {
        isUserExistOrThrowNotFound(userId);
        return itemRepository.findItemById(id)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    public List<ItemDto> getAllItemsByUserId(Long userId) {
        isUserExistOrThrowNotFound(userId);
        return itemRepository.findItemsByOwner_Id(userId).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    public List<ItemDto> searchItems(Long userId, String query) {
        isUserExistOrThrowNotFound(userId);
        return itemRepository.searchItemsByQuery(query).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    public ItemDto createItem(NewItemRequest request, Long userId) {
        User user = isUserExistOrThrowNotFound(userId);
        Item item = ItemMapper.toItem(request);
        item.setOwner(user);
        item = itemRepository.save(item);

        return ItemMapper.toItemDto(item);
    }
//
//    public ItemDto updateItem(Long userId, Long itemId, UpdateItemRequest request) {
//        getItemById(userId, itemId);
//        Item newItem = itemRepository.findAllFromUserId(userId).stream()
//                .filter(item -> item.getId().equals(itemId))
//                .findFirst()
//                .map(item -> ItemMapper.updateItemFields(item, request))
//                .orElseThrow(() -> new AccessDeniedException("Cannot access to this item"));
//        newItem = itemRepository.updateItem(newItem);
//
//        return ItemMapper.toItemDto(newItem);
//
//    }

    private User isUserExistOrThrowNotFound(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("X-Sharer-User-Id not found"));
    }
}
