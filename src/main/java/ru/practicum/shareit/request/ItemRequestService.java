package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.user.UserValidationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserValidationService userValidationService;

    public ItemRequestDto getItemRequest(Long userId, Long id) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        return itemRequestRepository.findItemRequestById(id).
                map(ItemRequestMapper::toItemRequestDto).
                orElseThrow(() -> new NotFoundException("ItemRequest not found"));
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
}
