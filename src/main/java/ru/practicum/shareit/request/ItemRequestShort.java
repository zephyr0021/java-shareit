package ru.practicum.shareit.request;

import java.time.OffsetDateTime;
import java.util.List;

public interface ItemRequestShort {
    Long getId();

    String getDescription();

    OffsetDateTime getCreated();

    List<ItemInfo> getItems();

    interface ItemInfo {
        Long getId();
        String getName();
        OwnerInfo getOwner();

        interface OwnerInfo {
            Long getId();
        }
    }
}
