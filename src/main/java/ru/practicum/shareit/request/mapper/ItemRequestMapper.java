package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.item.dto.ItemDtoForItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.ItemRequestShort;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public final class ItemRequestMapper {

    public static ItemRequestDto toItemRequestDto(ItemRequestShort itemRequestShort) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequestShort.getId());
        itemRequestDto.setDescription(itemRequestShort.getDescription());
        itemRequestDto.setCreated(itemRequestShort.getCreated());
        List<ItemDtoForItemRequest> items = itemRequestShort.getItems()
                .stream()
                .map(ItemMapper::toItemDtoForItemRequest).toList();
        itemRequestDto.setItems(items);

        return itemRequestDto;
    }
}
