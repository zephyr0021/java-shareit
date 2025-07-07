package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.NewBookingRequestDto;
import ru.practicum.shareit.client.BaseClient;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> getBooking(Long id, Long userId) {
        return get(API_PREFIX + "/" + id, userId);
    }

    public ResponseEntity<Object> getBookingByBooker(Long userId, BookingState state) {
        return get(API_PREFIX + "?state=" + state, userId);
    }

    public ResponseEntity<Object> getBookingByOwner(Long userId, BookingState state) {
        return get(API_PREFIX + "/owner?state=" + state, userId);
    }

    public ResponseEntity<Object> createBooking(NewBookingRequestDto request, Long userId) {
        return post(API_PREFIX, userId, request);
    }

    public ResponseEntity<Object> approveBooking(Long id, Long userId, Boolean approved) {
        return patch(API_PREFIX + "/" + id + "?approved=" + approved, userId, null);
    }

}
