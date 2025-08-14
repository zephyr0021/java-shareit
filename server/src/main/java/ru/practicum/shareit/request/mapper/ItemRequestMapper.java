package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.item.dto.ItemDtoForItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.ItemRequestShort;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.OffsetDateTime;
import java.util.List;

public final class ItemRequestMapper {

    public static ItemRequestDto toItemRequestDto(ItemRequestShort itemRequestShort) {
        ItemRequestDto itemRequestDto = fillItemRequestDto(itemRequestShort.getId(),
                itemRequestShort.getDescription(), itemRequestShort.getCreated());
        List<ItemDtoForItemRequest> items = itemRequestShort.getItems()
                .stream()
                .map(ItemMapper::toItemDtoForItemRequest).toList();
        itemRequestDto.setItems(items);

        return itemRequestDto;
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = fillItemRequestDto(itemRequest.getId(),
                itemRequest.getDescription(), itemRequest.getCreated());
        List<ItemDtoForItemRequest> items = itemRequest.getItems()
                .stream()
                .map(ItemMapper::toItemDtoForItemRequest).toList();
        itemRequestDto.setItems(items);

        return itemRequestDto;

    }


    public static ItemRequest toItemRequest(NewItemRequestRequest request) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(request.getDescription());

        return itemRequest;
    }

    public static ItemRequestDto fillItemRequestDto(Long id, String description, OffsetDateTime created) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(id);
        itemRequestDto.setDescription(description);
        itemRequestDto.setCreated(created);

        return itemRequestDto;
    }
}
