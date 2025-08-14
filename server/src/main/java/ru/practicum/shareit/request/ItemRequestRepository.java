package ru.practicum.shareit.request;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;
import java.util.Optional;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    Optional<ItemRequestShort> findItemRequestById(Long id);

    List<ItemRequestShort> findItemRequestsByRequestorId(Long userId, Sort sort);

    List<ItemRequestShort> findItemRequestByRequestorIdNot(Long userId, Sort sort);
}
