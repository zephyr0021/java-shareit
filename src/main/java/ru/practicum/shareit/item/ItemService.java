package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserValidationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserValidationService userValidationService;

    public ItemDto getItemById(Long userId, Long id) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        return itemRepository.findItemById(id)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    public List<ItemDto> getAllItemsByUserId(Long userId) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        return itemRepository.findItemsByOwner_Id(userId).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    public List<ItemDto> searchItems(Long userId, String query) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        return itemRepository.searchItemsByQuery(query).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Transactional
    public ItemDto createItem(NewItemRequest request, Long userId) {
        User user = userValidationService.isUserExistOrThrowNotFound(userId);
        Item item = ItemMapper.toItem(request);
        item.setOwner(user);
        item = itemRepository.save(item);

        return ItemMapper.toItemDto(item);
    }

    @Transactional
    public ItemDto updateItem(Long userId, Long itemId, UpdateItemRequest request) {
        getItemById(userId, itemId);
        Item newItem = itemRepository.findItemsByOwner_Id(userId).stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .map(ItemMapper::toItem)
                .map(item -> ItemMapper.updateItemFields(item, request))
                .orElseThrow(() -> new AccessDeniedException("Cannot access to this item"));
        newItem = itemRepository.save(newItem);

        return ItemMapper.toItemDto(newItem);

    }
}
