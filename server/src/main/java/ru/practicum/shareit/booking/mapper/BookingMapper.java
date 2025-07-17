package ru.practicum.shareit.booking.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingShort;
import ru.practicum.shareit.booking.BookingShortForItem;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingForItemDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookingMapper {
    public static BookingDto toBookingDto(BookingShort booking) {
        return fillBookingDto(booking);
    }

    public static BookingDto toBookingDto(Booking booking) {
        return fillBookingDto(booking);
    }

    public static BookingForItemDto toBookingForItemDto(BookingShortForItem booking) {
        BookingForItemDto bookingForItemDto = new BookingForItemDto();

        bookingForItemDto.setId(booking.getId());
        bookingForItemDto.setBookerId(booking.getBooker().getId());
        bookingForItemDto.setStart(booking.getStart());
        bookingForItemDto.setEnd(booking.getEnd());

        return bookingForItemDto;
    }

    public static Booking toBooking(NewBookingRequest request, User booker, Item item) {
        return fillBooking(request, booker, item);
    }

    private static BookingDto fillBookingDto(BookingShort booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setStatus(booking.getStatus());

        BookingDto.Booker booker = new BookingDto.Booker();
        booker.setId(booking.getBooker().getId());
        bookingDto.setBooker(booker);

        BookingDto.Item item = new BookingDto.Item();
        item.setId(booking.getItem().getId());
        item.setName(booking.getItem().getName());
        bookingDto.setItem(item);

        return bookingDto;
    }

    private static BookingDto fillBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setStatus(booking.getStatus());

        BookingDto.Booker booker = new BookingDto.Booker();
        booker.setId(booking.getBooker().getId());
        bookingDto.setBooker(booker);

        BookingDto.Item item = new BookingDto.Item();
        item.setId(booking.getItem().getId());
        item.setName(booking.getItem().getName());
        bookingDto.setItem(item);

        return bookingDto;
    }

    private static Booking fillBooking(NewBookingRequest request, User booker, Item item) {
        Booking booking = new Booking();
        booking.setStart(request.getStart());
        booking.setEnd(request.getEnd());
        booking.setBooker(booker);
        booking.setItem(item);

        return booking;
    }
}
