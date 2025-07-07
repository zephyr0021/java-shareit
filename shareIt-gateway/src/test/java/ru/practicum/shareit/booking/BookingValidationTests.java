package ru.practicum.shareit.booking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.NewBookingRequestDto;


import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingValidationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingClient bookingClient;

    @Test
    void createBookingWithoutItemId() throws Exception {
        var booking = new NewBookingRequestDto(null, OffsetDateTime.now().plusMinutes(5), OffsetDateTime.now().plusMinutes(10));
        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(booking)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation error"))
                .andExpect(jsonPath("$.message").value("itemId must not be null"));
        Mockito.verify(bookingClient, Mockito.never()).createBooking(any(), anyLong());
    }

    @Test
    void createBookingWithoutStart() throws Exception {
        var booking = new NewBookingRequestDto(1L, null, OffsetDateTime.now().plusMinutes(10));
        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(booking)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation error"))
                .andExpect(jsonPath("$.message").value("start must not be null"));
        Mockito.verify(bookingClient, Mockito.never()).createBooking(any(), anyLong());
    }

    @Test
    void createBookingWithoutEnd() throws Exception {
        var booking = new NewBookingRequestDto(1L, OffsetDateTime.now().plusMinutes(5), null);
        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(booking)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation error"))
                .andExpect(jsonPath("$.message").value("end must not be null"));
        Mockito.verify(bookingClient, Mockito.never()).createBooking(any(), anyLong());
    }

    @Test
    void createBookingWithStartPast() throws Exception {
        var booking = new NewBookingRequestDto(1L, OffsetDateTime.now().minusNanos(10), OffsetDateTime.now().plusMinutes(10));
        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(booking)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation error"))
                .andExpect(jsonPath("$.message").value("start must be a date in the present or in the future"));
        Mockito.verify(bookingClient, Mockito.never()).createBooking(any(), anyLong());
    }

    @Test
    void createBookingWithEndPast() throws Exception {
        var booking = new NewBookingRequestDto(1L, OffsetDateTime.now().plusMinutes(5), OffsetDateTime.now().minusNanos(10));
        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(booking)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("validation error"))
                .andExpect(jsonPath("$.message").value("end must be a future date"));
        Mockito.verify(bookingClient, Mockito.never()).createBooking(any(), anyLong());
    }

    @Test
    void getBookingByBookerWithInvalidState() throws Exception {
        mvc.perform(get("/bookings?state=TEST")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("bad parameter value"))
                .andExpect(jsonPath("$.message").value("Bad parameter state value"));
        Mockito.verify(bookingClient, Mockito.never()).getBookingByBooker(anyLong(), any());
    }

    @Test
    void getBookingByOwnerWithInvalidState() throws Exception {
        mvc.perform(get("/bookings/owner?state=TEST")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isBadRequest());
        Mockito.verify(bookingClient, Mockito.never()).getBookingByOwner(anyLong(), any());
    }

    @Test
    void invalidApproveBooking() throws Exception {
        mvc.perform(patch("/bookings/1?approved=test")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("bad parameter value"))
                .andExpect(jsonPath("$.message").value("Bad parameter approved value"));
        Mockito.verify(bookingClient, Mockito.never()).approveBooking(anyLong(), anyLong(), anyBoolean());
    }

    private String asJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
