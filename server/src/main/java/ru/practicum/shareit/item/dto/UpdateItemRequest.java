package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemRequest {
    private String name;
    private String description;
    private Boolean available;

    public boolean hasName() {
        return !(name == null || name.isEmpty());
    }

    public boolean hasDescription() {
        return !(description == null || description.isEmpty());
    }

    public boolean hasAvailable() {
        return !(available == null);
    }
}
