package ru.practicum.shareit.item;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingShortForItem;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserValidationService;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private ItemService itemService;

    private ItemShort itemShort;
    private ItemShort itemShort2;
    private Item item2;
    private Comment comment1;
    private Comment comment2;
    private BookingShortForItem bookingShortForItem1;
    private BookingShortForItem bookingShortForItem2;
    private BookingShortForItem.UserInfo userInfo1;
    private BookingShortForItem.UserInfo userInfo2;

    @BeforeEach
    void setUp() {
        itemShort = mock(ItemShort.class);
        itemShort2 = mock(ItemShort.class);
        bookingShortForItem1 = mock(BookingShortForItem.class);
        bookingShortForItem2 = mock(BookingShortForItem.class);
        userInfo1 = mock(BookingShortForItem.UserInfo.class);
        userInfo2 = mock(BookingShortForItem.UserInfo.class);
        item2 = Instancio.create(Item.class);
        comment1 = Instancio.create(Comment.class);
        comment2 = Instancio.create(Comment.class);
    }

    @Test
    void getItemById() {
        when(itemShort.getId()).thenReturn(1L);
        when(itemShort.getName()).thenReturn("Test item");
        when(itemShort.getDescription()).thenReturn("Description");
        when(itemShort.getAvailable()).thenReturn(true);
        when(itemRepository.findItemById(itemShort.getId())).thenReturn(Optional.of(itemShort));
        when(commentRepository.findAllByItemId(itemShort.getId())).thenReturn(List.of(comment1, comment2));

        ItemWithBookingsAndCommentsDto item = itemService.getItemById(1L, 1L);
        assertEquals(itemShort.getId(), item.getId());
        assertEquals(itemShort.getName(), item.getName());
        assertEquals(itemShort.getDescription(), item.getDescription());
        assertEquals(itemShort.getAvailable(), item.getAvailable());
        assertEquals(comment1.getId(), item.getComments().get(0).getId());
        assertEquals(comment2.getId(), item.getComments().get(1).getId());
    }

    @Test
    void getAllItemsByUserId() {
        String timezone = "Asia/Yekaterinburg";
        ReflectionTestUtils.setField(itemService, "timezone", timezone);
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of(timezone));
        when(itemShort.getId()).thenReturn(1L);
        when(itemShort.getName()).thenReturn("Test item");
        when(itemShort.getDescription()).thenReturn("Description");
        when(itemShort.getAvailable()).thenReturn(true);

        when(itemShort2.getId()).thenReturn(2L);
        when(itemShort2.getName()).thenReturn("Test item2");
        when(itemShort2.getDescription()).thenReturn("Description2");
        when(itemShort2.getAvailable()).thenReturn(false);

        when(userInfo1.getId()).thenReturn(1L);
        when(userInfo2.getId()).thenReturn(2L);

        when(bookingShortForItem1.getId()).thenReturn(1L);
        when(bookingShortForItem1.getBooker()).thenReturn(userInfo1);
        when(bookingShortForItem1.getEnd()).thenReturn(now.plusDays(1));
        when(bookingShortForItem1.getStart()).thenReturn(now.plusMinutes(30));

        when(bookingShortForItem2.getId()).thenReturn(2L);
        when(bookingShortForItem2.getBooker()).thenReturn(userInfo2);
        when(bookingShortForItem2.getEnd()).thenReturn(now.plusDays(2));
        when(bookingShortForItem2.getStart()).thenReturn(now.plusMinutes(40));

        when(itemRepository.findItemsByOwnerId(1L)).thenReturn(List.of(itemShort, itemShort2));
        when(bookingRepository.findLastBookingForItem(eq(1L), any(OffsetDateTime.class), any(Pageable.class))).thenReturn(List.of(bookingShortForItem1));
        when(bookingRepository.findNextBookingForItem(eq(1L), any(OffsetDateTime.class), any(Pageable.class))).thenReturn(List.of(bookingShortForItem2));
        when(commentRepository.findAllByItemId(itemShort.getId())).thenReturn(List.of(comment1, comment2));
        when(bookingRepository.findLastBookingForItem(eq(2L), any(OffsetDateTime.class), any(Pageable.class))).thenReturn(List.of(bookingShortForItem1));
        when(bookingRepository.findNextBookingForItem(eq(2L), any(OffsetDateTime.class), any(Pageable.class))).thenReturn(List.of(bookingShortForItem1));
        when(commentRepository.findAllByItemId(itemShort2.getId())).thenReturn(List.of(comment1, comment2));

        List<ItemWithBookingsAndCommentsDto> items = itemService.getAllItemsByUserId(1L);
        assertEquals(2, items.size());

        ItemWithBookingsAndCommentsDto itemWithBookingsAndCommentsDto = items.getFirst();
        assertEquals(itemShort.getId(), itemWithBookingsAndCommentsDto.getId());
        assertEquals(itemShort.getName(), itemWithBookingsAndCommentsDto.getName());
        assertEquals(itemShort.getDescription(), itemWithBookingsAndCommentsDto.getDescription());
        assertEquals(itemShort.getAvailable(), itemWithBookingsAndCommentsDto.getAvailable());
        assertEquals(bookingShortForItem1.getId(), itemWithBookingsAndCommentsDto.getLastBooking().getId());
        assertEquals(bookingShortForItem1.getStart(), itemWithBookingsAndCommentsDto.getLastBooking().getStart());
        assertEquals(bookingShortForItem1.getEnd(), itemWithBookingsAndCommentsDto.getLastBooking().getEnd());
        assertEquals(bookingShortForItem1.getBooker().getId(), itemWithBookingsAndCommentsDto.getLastBooking().getBookerId());
        assertEquals(bookingShortForItem2.getId(), itemWithBookingsAndCommentsDto.getNextBooking().getId());
        assertEquals(bookingShortForItem2.getStart(), itemWithBookingsAndCommentsDto.getNextBooking().getStart());
        assertEquals(bookingShortForItem2.getEnd(), itemWithBookingsAndCommentsDto.getNextBooking().getEnd());
        assertEquals(bookingShortForItem2.getBooker().getId(), itemWithBookingsAndCommentsDto.getNextBooking().getBookerId());
    }

    @Test
    void createItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item2);

        NewItemRequest request = new NewItemRequest(item2.getName(), item2.getDescription(), item2.getAvailable(), item2.getRequestId());

        ItemDto item = itemService.createItem(request, 2L);

        assertEquals(item2.getId(), item.getId());
        assertEquals(item2.getName(), item.getName());
        assertEquals(item2.getDescription(), item.getDescription());
        assertEquals(item2.getAvailable(), item.getAvailable());
    }

    @Test
    void searchItems() {
        when(itemRepository.searchItemsByQuery(anyString())).thenReturn(List.of(itemShort, itemShort2));
        List<ItemDto> searchedItems = itemService.searchItems(1L, "test");
        assertEquals(2, searchedItems.size());
        ItemDto itemDto = searchedItems.getFirst();
        assertEquals(itemShort.getId(), itemDto.getId());
        assertEquals(itemShort.getName(), itemDto.getName());
        assertEquals(itemShort.getDescription(), itemDto.getDescription());
        assertEquals(itemShort.getAvailable(), itemDto.getAvailable());
        ItemDto itemDto2 = searchedItems.get(1);
        assertEquals(itemShort2.getId(), itemDto2.getId());
        assertEquals(itemShort2.getName(), itemDto2.getName());
        assertEquals(itemShort2.getDescription(), itemDto2.getDescription());
        assertEquals(itemShort2.getAvailable(), itemDto2.getAvailable());

    }

    @Test
    void createItemWithoutRequestId() {
        when(itemRepository.save(any(Item.class))).thenReturn(item2);

        NewItemRequest request = new NewItemRequest(item2.getName(), item2.getDescription(), item2.getAvailable(), null);

        ItemDto item = itemService.createItem(request, 2L);

        assertEquals(item2.getId(), item.getId());
        assertEquals(item2.getName(), item.getName());
        assertEquals(item2.getDescription(), item.getDescription());
        assertEquals(item2.getAvailable(), item.getAvailable());

    }

    @Test
    void updateItem() {
        when(itemShort.getId()).thenReturn(item2.getId());
        when(itemShort.getName()).thenReturn(item2.getName());
        when(itemShort.getDescription()).thenReturn(item2.getDescription());
        when(itemShort.getAvailable()).thenReturn(item2.getAvailable());
        when(itemRepository.save(any(Item.class))).thenReturn(item2);
        when(itemRepository.findItemById(item2.getId())).thenReturn(Optional.of(itemShort));
        when(itemRepository.findItemsByOwnerId(1L)).thenReturn(List.of(itemShort));

        UpdateItemRequest request = new UpdateItemRequest(item2.getName(), item2.getDescription(), item2.getAvailable());

        ItemDto updItem = itemService.updateItem(1L, item2.getId(), request);

        assertEquals(item2.getId(), updItem.getId());
        assertEquals(item2.getName(), updItem.getName());
        assertEquals(item2.getDescription(), updItem.getDescription());
        assertEquals(item2.getAvailable(), updItem.getAvailable());
    }

    @Test
    void cannotUpdateItemAcess() {
        when(itemShort.getId()).thenReturn(item2.getId());
        when(itemShort.getName()).thenReturn(item2.getName());
        when(itemShort.getDescription()).thenReturn(item2.getDescription());
        when(itemShort.getAvailable()).thenReturn(item2.getAvailable());
        when(itemRepository.findItemById(item2.getId())).thenReturn(Optional.of(itemShort));
        when(itemRepository.findItemsByOwnerId(1L)).thenReturn(List.of());

        UpdateItemRequest request = new UpdateItemRequest(item2.getName(), item2.getDescription(), item2.getAvailable());

        assertThrows(AccessDeniedException.class, () -> itemService.updateItem(1L, item2.getId(), request));
    }
}
