package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTests {
    @Mock
    private ItemRequestRepository itemRequestRepository;

    @InjectMocks
    private ItemRequestService itemRequestService;

    private ItemRequestShort itemRequestShort;

    @BeforeEach
    void setUp() {
        lenient().when(itemRequestShort.getId()).thenReturn(1L);
        lenient().when(itemRequestShort.getDescription()).thenReturn("description");
    }



}
