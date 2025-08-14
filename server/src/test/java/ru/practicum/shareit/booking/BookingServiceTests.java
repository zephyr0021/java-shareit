package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.ServerException;
import ru.practicum.shareit.item.ItemValidationService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserValidationService;
import ru.practicum.shareit.user.model.User;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTests {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemValidationService itemValidationService;

    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private BookingService bookingService;

    private BookingShort bookingShort;
    private User user;
    private Item item;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(bookingService, "timezone", "UTC");
        bookingShort = mock(BookingShort.class);
        lenient().when(bookingShort.getId()).thenReturn(1L);
        lenient().when(bookingShort.getStart()).thenReturn(OffsetDateTime.now());
        lenient().when(bookingShort.getEnd()).thenReturn(OffsetDateTime.now().plusDays(1));
        lenient().when(bookingShort.getStatus()).thenReturn(BookingStatus.APPROVED);
        BookingShort.ItemInfo itemInfo = mock(BookingShort.ItemInfo.class);
        lenient().when(itemInfo.getId()).thenReturn(1L);
        lenient().when(itemInfo.getName()).thenReturn("name");
        lenient().when(bookingShort.getItem()).thenReturn(itemInfo);
        BookingShort.UserInfo userInfo = mock(BookingShort.UserInfo.class);
        lenient().when(userInfo.getId()).thenReturn(1L);
        lenient().when(bookingShort.getBooker()).thenReturn(userInfo);

        user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("email");
        item = new Item();
        item.setId(1L);
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);

    }

    @Test
    void getBookingById() {
        when(bookingRepository.findByItemOwnerOrBooker(1L, 1L)).thenReturn(Optional.of(bookingShort));
        BookingDto booking = bookingService.getBookingById(1L, 1L);

        assertEquals(bookingShort.getId(), booking.getId());
        assertEquals(bookingShort.getStart(), booking.getStart());
        assertEquals(bookingShort.getEnd(), booking.getEnd());
        assertEquals(bookingShort.getStatus(), booking.getStatus());
        assertEquals(bookingShort.getItem().getId(), booking.getItem().getId());
        assertEquals(bookingShort.getItem().getName(), bookingShort.getItem().getName());
        assertEquals(bookingShort.getBooker().getId(), bookingShort.getBooker().getId());
    }

    @Test
    void getAllBookingsByBookerStateRejected() {
        when(bookingRepository.findByBookerIdAndStatus(eq(1L), eq(BookingStatus.REJECTED), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByBooker(1L, "REJECTED");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByBookerStateCurrent() {
        when(bookingRepository.findByBookerIdAndStatusAndStartBeforeAndEndAfter(eq(1L), eq(BookingStatus.APPROVED), any(OffsetDateTime.class), any(OffsetDateTime.class), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByBooker(1L, "CURRENT");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByBookerStatePast() {
        when(bookingRepository.findByBookerIdAndStatusAndEndBefore(eq(1L), eq(BookingStatus.APPROVED), any(OffsetDateTime.class), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByBooker(1L, "PAST");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByBookerStateFuture() {
        when(bookingRepository.findByBookerIdAndStatusAndStartAfter(eq(1L), eq(BookingStatus.APPROVED), any(OffsetDateTime.class), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByBooker(1L, "FUTURE");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByOwnerStateAll() {
        when(bookingRepository.findByItemOwnerId(eq(1L), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByOwner(1L, "ALL");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByOwnerStateWaiting() {
        when(bookingRepository.findByItemOwnerIdAndStatus(eq(1L), eq(BookingStatus.WAITING), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByOwner(1L, "WAITING");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByOwnerStateRejected() {
        when(bookingRepository.findByItemOwnerIdAndStatus(eq(1L), eq(BookingStatus.REJECTED), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByOwner(1L, "REJECTED");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByOwnerStateCurrent() {
        when(bookingRepository.findByItemOwnerIdAndStatusAndStartBeforeAndEndAfter(eq(1L), eq(BookingStatus.APPROVED), any(OffsetDateTime.class), any(OffsetDateTime.class), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByOwner(1L, "CURRENT");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByOwnerStatePast() {
        when(bookingRepository.findByItemOwnerIdAndStatusAndEndBefore(eq(1L), eq(BookingStatus.APPROVED), any(OffsetDateTime.class), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByOwner(1L, "PAST");
        assertEquals(1, bookings.size());
    }

    @Test
    void getAllBookingsByOwnerStateFuture() {
        when(bookingRepository.findByItemOwnerIdAndStatusAndStartAfter(eq(1L), eq(BookingStatus.APPROVED), any(OffsetDateTime.class), any(Sort.class))).thenReturn(List.of(bookingShort));
        Collection<BookingDto> bookings = bookingService.getAllBookingsByOwner(1L, "FUTURE");
        assertEquals(1, bookings.size());
    }

    @Test
    void createBooking() {
        when(userValidationService.isUserExistOrThrowNotFound(1L)).thenReturn(user);
        when(itemValidationService.isItemExistOrThrowNotFound(1L)).thenReturn(item);
        NewBookingRequest request = new NewBookingRequest(1L, OffsetDateTime.now(), OffsetDateTime.now().plusDays(1));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setId(1L);
            return b;
        });

        BookingDto booking = bookingService.createBooking(request, 1L);
        assertEquals(1L, booking.getId());
        assertEquals(BookingStatus.WAITING, booking.getStatus());
        assertEquals(request.getStart(), booking.getStart());
        assertEquals(request.getEnd(), booking.getEnd());
        assertEquals(request.getItemId(), booking.getItem().getId());
        assertEquals(item.getName(), booking.getItem().getName());
        assertEquals(user.getId(), booking.getBooker().getId());
    }

    @Test
    void approveBooking() {
        when(bookingRepository.updateStatusByIdAndOwnerId(1L, 1L, BookingStatus.APPROVED)).thenReturn(1);
        when(bookingRepository.findBookingById(1L)).thenReturn(Optional.of(bookingShort));
        BookingDto booking = bookingService.approveBooking(1L, 1L, true);
        assertEquals(1L, booking.getId());
    }

    @Test
    void approveBookingNotFound() {
        when(bookingRepository.updateStatusByIdAndOwnerId(1L, 1L, BookingStatus.APPROVED)).thenReturn(0);
        assertThrows(ServerException.class, () -> bookingService.approveBooking(1L, 1L, true));
    }

    @Test
    void createBookingNotAvailableItem() {
        item.setAvailable(false);
        when(userValidationService.isUserExistOrThrowNotFound(1L)).thenReturn(user);
        when(itemValidationService.isItemExistOrThrowNotFound(1L)).thenReturn(item);
        NewBookingRequest request = new NewBookingRequest(1L, OffsetDateTime.now(), OffsetDateTime.now().plusDays(1));
        assertThrows(ServerException.class, () -> bookingService.createBooking(request, 1L));
        Mockito.verify(bookingRepository, Mockito.never()).save(any(Booking.class));
    }

    @Test
    void getAllBookingsByOwnerInvalidState() {
        assertTrue(bookingService.getAllBookingsByOwner(1L, "TEST").isEmpty());
        Mockito.verifyNoInteractions(bookingRepository);
    }

    @Test
    void getAllBookingsByBooker() {
        assertTrue(bookingService.getAllBookingsByBooker(1L, "TEST").isEmpty());
        Mockito.verifyNoInteractions(bookingRepository);
    }


}
