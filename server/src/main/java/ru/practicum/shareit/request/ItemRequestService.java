package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserValidationService;
import ru.practicum.shareit.user.model.User;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserValidationService userValidationService;
    @Value("${app.timezone}")
    private String timezone;

    public ItemRequestDto getItemRequest(Long userId, Long id) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        return itemRequestRepository.findItemRequestById(id)
                .map(ItemRequestMapper::toItemRequestDto)
                .orElseThrow(() -> new NotFoundException("ItemRequest not found"));
    }

    public List<ItemRequestDto> getUserItemRequests(Long userId) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        return itemRequestRepository.findItemRequestsByRequestorId(userId, sort)
                .stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .toList();
    }

    public List<ItemRequestDto> getAllItemRequests(Long userId) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        return itemRequestRepository.findItemRequestByRequestorIdNot(userId, sort)
                .stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .toList();
    }

    @Transactional
    public ItemRequestDto createItemRequest(Long userId, NewItemRequestRequest request) {
        User requestor = userValidationService.isUserExistOrThrowNotFound(userId);
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of(timezone));
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(request);
        itemRequest.setCreated(now);
        itemRequest.setRequestor(requestor);
        itemRequest.setItems(List.of());

        itemRequest = itemRequestRepository.save(itemRequest);

        return ItemRequestMapper.toItemRequestDto(itemRequest);
    }
}
