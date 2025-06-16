package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.ItemShort;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemMapper {
    public static Item toItem(NewItemRequest request) {
        return fillItem(request.getName(), request.getDescription(), request.getAvailable());
    }

    public static Item toItem(ItemShort itemShort) {
        return fillItem(itemShort.getName(), itemShort.getDescription(), itemShort.getAvailable());
    }

    public static ItemDto toItemDto(ItemShort item) {
        return fillItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }

    public static ItemDto toItemDto(Item item) {
        return fillItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }

    public static Item updateItemFields(Item item, UpdateItemRequest request) {
        if (request.hasName()) {
            item.setName(request.getName());
        }

        if (request.hasDescription()) {
            item.setDescription(request.getDescription());
        }

        if (request.hasAvailable()) {
            item.setAvailable(request.getAvailable());
        }

        return item;
    }

    private static ItemDto fillItemDto(Long id, String name, String description, Boolean available) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(id);
        itemDto.setName(name);
        itemDto.setDescription(description);
        itemDto.setAvailable(available);
        return itemDto;
    }

    private static Item fillItem(String name, String description, Boolean available) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setAvailable(available);

        return item;
    }

}
