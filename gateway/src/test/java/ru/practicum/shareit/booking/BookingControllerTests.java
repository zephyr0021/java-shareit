package ru.practicum.shareit.booking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.NewBookingRequestDto;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingClient bookingClient;

    @Test
    void getBooking() throws Exception {
        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(bookingClient, Mockito.times(1)).getBooking(1L, 1L);
    }

    @Test
    void getBookingsByBooker() throws Exception {
        mvc.perform(get("/bookings?state=WAITING")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(bookingClient, Mockito.times(1)).getBookingByBooker(1L, BookingState.WAITING);
    }

    @Test
    void getBookingsByBookerWithoutState() throws Exception {
        mvc.perform(get("/bookings?state=")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(bookingClient, Mockito.times(1)).getBookingByBooker(1L, BookingState.ALL);
    }

    @Test
    void getBookingsByOwner() throws Exception {
        mvc.perform(get("/bookings/owner?state=REJECTED")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(bookingClient, Mockito.times(1)).getBookingByOwner(1L, BookingState.REJECTED);
    }

    @Test
    void getBookingsByOwnerWithoutState() throws Exception {
        mvc.perform(get("/bookings/owner?state=")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(bookingClient, Mockito.times(1)).getBookingByOwner(1L, BookingState.ALL);
    }

    @Test
    void createBooking() throws Exception {
        var booking = new NewBookingRequestDto(1L, OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(10), OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(30));
        when(bookingClient.createBooking(booking, 1L)).thenReturn(
                ResponseEntity.status(HttpStatus.CREATED).body(asJson(booking))
        );

        mvc.perform(post("/bookings")
                .header("X-Sharer-User-Id", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(booking)))
                .andExpect(status().isCreated());

        Mockito.verify(bookingClient, Mockito.times(1)).createBooking(
                argThat(dto ->
                        dto.getItemId().equals(booking.getItemId()) &&
                                dto.getStart().toInstant().equals(booking.getStart().toInstant()) &&
                                dto.getEnd().toInstant().equals(booking.getEnd().toInstant())
                ),
                eq(1L));
    }

    @Test
    void approveBooking() throws Exception {
        mvc.perform(patch("/bookings/1?approved=true")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());

        Mockito.verify(bookingClient, Mockito.times(1)).approveBooking(1L, 1L, true);
    }

    private String asJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
