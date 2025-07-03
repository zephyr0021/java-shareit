package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.NewBookingRequestDto;
import ru.practicum.shareit.client.BaseClient;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getBooking(Long id, Long userId) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> getBookingByBooker(Long userId, BookingState state) {
        return get("?state=" + state, userId);
    }

    public ResponseEntity<Object> getBookingByOwner(Long userId, BookingState state) {
        return get("/owner?state=" + state, userId);
    }

    public ResponseEntity<Object> createBooking(NewBookingRequestDto request, Long userId) {
        return post("", userId, request);
    }

    public ResponseEntity<Object> approveBooking(Long id, Long userId, Boolean approved) {
        return patch("/" + id + "?approved=" + approved, userId, null);
    }

}
