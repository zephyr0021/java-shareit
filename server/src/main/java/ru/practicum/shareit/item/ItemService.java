package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingForItemDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ServerException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserValidationService;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserValidationService userValidationService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;
    @Value("${app.timezone}")
    private String timezone;

    public ItemWithBookingsAndCommentsDto getItemById(Long userId, Long id) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        return itemRepository.findItemById(id)
                .map(itemShort -> {
                    List<CommentDto> comments = commentRepository.findAllByItemId(id)
                            .stream()
                            .map(CommentMapper::toCommentDto)
                            .toList();

                    return ItemMapper.toItemWithBookingsAndCommentsDto(itemShort, comments);
                })
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    public List<ItemWithBookingsAndCommentsDto> getAllItemsByUserId(Long userId) {
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of(timezone));
        userValidationService.isUserExistOrThrowNotFound(userId);
        Pageable lastBookingPage = PageRequest.of(0, 1, Sort.by("end").descending());
        Pageable nextBookingPage = PageRequest.of(0, 1, Sort.by("start").ascending());
        return itemRepository.findItemsByOwnerId(userId).stream()
                .map(itemShort -> {
                    Long id = itemShort.getId();
                    BookingForItemDto lastBooking = bookingRepository.findLastBookingForItem(id, now, lastBookingPage)
                            .stream()
                            .findFirst()
                            .map(BookingMapper::toBookingForItemDto)
                            .orElse(null);
                    BookingForItemDto nextBooking = bookingRepository.findNextBookingForItem(id, now, nextBookingPage)
                            .stream()
                            .findFirst()
                            .map(BookingMapper::toBookingForItemDto)
                            .orElse(null);
                    List<CommentDto> comments = commentRepository.findAllByItemId(id)
                            .stream()
                            .map(CommentMapper::toCommentDto)
                            .toList();

                    return ItemMapper.toItemWithBookingsAndCommentsDto(itemShort, lastBooking, nextBooking, comments);
                })
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
        ItemRequest itemRequest = null;
        if (request.getRequestId() != null) {
            itemRequest = itemRequestRepository.findById(request.getRequestId()).orElse(null);
        }
        item.setOwner(user);
        item.setRequest(itemRequest);
        item = itemRepository.save(item);

        return ItemMapper.toItemDto(item);
    }

    @Transactional
    public ItemDto updateItem(Long userId, Long itemId, UpdateItemRequest request) {
        getItemById(userId, itemId);
        Item newItem = itemRepository.findItemsByOwnerId(userId).stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .map(ItemMapper::toItem)
                .map(item -> ItemMapper.updateItemFields(item, request))
                .orElseThrow(() -> new AccessDeniedException("Cannot access to this item"));
        newItem = itemRepository.save(newItem);

        return ItemMapper.toItemDto(newItem);
    }

    @Transactional
    public CommentDto setComment(Long userId, Long itemId, NewCommentRequest request) {
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of(timezone));
        User user = userValidationService.isUserExistOrThrowNotFound(userId);
        Item item  = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        Pageable lastBookingPage = PageRequest.of(0, 1, Sort.by("end").descending());
        BookingForItemDto lastBooking = bookingRepository.findLastBookingForItem(itemId, now, lastBookingPage)
                .stream()
                .findFirst()
                .map(BookingMapper::toBookingForItemDto)
                .orElse(null);

        if (lastBooking == null || lastBooking.getEnd().isAfter(now)) {
            throw new ServerException("Cannot comment this item");
        }

        Comment comment = CommentMapper.toComment(request);
        comment.setAuthor(user);
        comment.setCreated(now);
        comment.setItem(item);

        comment = commentRepository.save(comment);

        return CommentMapper.toCommentDto(comment);
    }
}
