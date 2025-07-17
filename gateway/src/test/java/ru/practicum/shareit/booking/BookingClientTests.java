package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.NewBookingRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingClientTests {
    @Autowired
    private BookingClient bookingClient;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void getBooking() {
        when(restTemplate.exchange(eq("/bookings/1"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        bookingClient.getBooking(1L, 1L);

        verify(restTemplate, times(1)).exchange(eq("/bookings/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void getBookingByBooker() {
        when(restTemplate.exchange(eq("/bookings?state=WAITING"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        bookingClient.getBookingByBooker(1L, BookingState.WAITING);

        verify(restTemplate, times(1)).exchange(eq("/bookings?state=WAITING"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void getBookingByOwner() {
        when(restTemplate.exchange(eq("/bookings/owner?state=PAST"), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        bookingClient.getBookingByOwner(1L, BookingState.PAST);

        verify(restTemplate, times(1)).exchange(eq("/bookings/owner?state=PAST"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void createBooking() {
        when(restTemplate.exchange(eq("/bookings"), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        bookingClient.createBooking(new NewBookingRequestDto(), 1L);

        verify(restTemplate, times(1)).exchange(eq("/bookings"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(Object.class));
    }

    @Test
    void approveBooking() {
        when(restTemplate.exchange(eq("/bookings/1?approved=true"), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        bookingClient.approveBooking(1L, 1L, true);

        verify(restTemplate, times(1)).exchange(eq("/bookings/1?approved=true"), eq(HttpMethod.PATCH),
                any(HttpEntity.class), eq(Object.class));
    }
}
