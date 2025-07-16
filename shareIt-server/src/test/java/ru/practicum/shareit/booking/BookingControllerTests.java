package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;
import ru.practicum.shareit.exception.ServerException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookingService bookingService;

    private BookingDto booking;
    private BookingDto booking2;
    private BookingDto booking3;

    private final String url = "/bookings";

    @BeforeEach
    void setUp() {
        booking = Instancio.create(BookingDto.class);
        booking2 = Instancio.create(BookingDto.class);
        booking3 = Instancio.create(BookingDto.class);
    }

    @Test
    void getBooking() throws Exception {
        String expectedJson = mapper.writeValueAsString(booking);
        when(bookingService.getBookingById(booking.getId(), 1L)).thenReturn(booking);
        mvc.perform(get(url + "/" + booking.getId())
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Mockito.verify(bookingService, Mockito.times(1)).getBookingById(booking.getId(), 1L);
    }

    @Test
    void getBookingsByBooker() throws Exception {
        String expectedJson = mapper.writeValueAsString(List.of(booking, booking2));
        when(bookingService.getAllBookingsByBooker(1L, "ALL")).thenReturn(List.of(booking, booking2));
        mvc.perform(get(url + "?state=ALL")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Mockito.verify(bookingService, Mockito.times(1)).getAllBookingsByBooker(1L, "ALL");
    }

    @Test
    void getBookingsByItemOwner() throws Exception {
        String expectedJson = mapper.writeValueAsString(List.of(booking, booking3));
        when(bookingService.getAllBookingsByOwner(1L, "ALL")).thenReturn(List.of(booking, booking3));
        mvc.perform(get(url + "/owner?state=ALL")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Mockito.verify(bookingService, Mockito.times(1)).getAllBookingsByOwner(1L, "ALL");
    }

    @Test
    void createBooking() throws Exception {
        NewBookingRequest request = new NewBookingRequest(booking.getItem().getId(), booking.getStart(), booking.getEnd());
        String expectedJson = mapper.writeValueAsString(booking);
        when(bookingService.createBooking(any(NewBookingRequest.class), eq(1L))).thenReturn(booking);
        mvc.perform(post(url)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request).replaceAll("Z", "")))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        Mockito.verify(bookingService, Mockito.times(1)).createBooking(any(NewBookingRequest.class), eq(1L));
    }

    @Test
    void approveBooking() throws Exception {
        String expectedJson = mapper.writeValueAsString(booking);
        when(bookingService.approveBooking(booking.getId(), 1L, true)).thenReturn(booking);
        mvc.perform(patch(url + "/" + booking.getId() + "?approved=true")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Mockito.verify(bookingService, Mockito.times(1)).approveBooking(booking.getId(), 1L, true);
    }

    @Test
    void createBookingItemNotAvailable() throws Exception {
        NewBookingRequest request = new NewBookingRequest(booking.getItem().getId(), booking.getStart(), booking.getEnd());
        when(bookingService.createBooking(any(NewBookingRequest.class), eq(1L))).thenThrow(new ServerException("Item is not available"));
        mvc.perform(post(url)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request).replaceAll("Z", "")))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("server error"))
                .andExpect(jsonPath("$.message").value("Item is not available"));
    }
}
