package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestRequest;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserValidationService;
import ru.practicum.shareit.user.model.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTests {
    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private ItemRequestService itemRequestService;

    private ItemRequestShort itemRequestShort;
    private ItemRequestShort itemRequestShort2;
    private ItemRequestShort.ItemInfo itemInfo;
    private ItemRequestShort.ItemInfo itemInfo2;
    private ItemRequestShort.ItemInfo itemInfo3;
    private ItemRequestShort.ItemInfo itemInfo4;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(itemRequestService, "timezone", "UTC");
        itemRequestShort = mock(ItemRequestShort.class);
        ItemRequestShort.ItemInfo.OwnerInfo ownerInfo = mock(ItemRequestShort.ItemInfo.OwnerInfo.class);
        itemInfo = mock(ItemRequestShort.ItemInfo.class);
        ItemRequestShort.ItemInfo.OwnerInfo ownerInfo2 = mock(ItemRequestShort.ItemInfo.OwnerInfo.class);
        itemInfo2 = mock(ItemRequestShort.ItemInfo.class);

        lenient().when(itemRequestShort.getId()).thenReturn(1L);
        lenient().when(itemRequestShort.getDescription()).thenReturn("description");
        lenient().when(itemRequestShort.getCreated()).thenReturn(OffsetDateTime.now());
        lenient().when(itemInfo.getId()).thenReturn(1L);
        lenient().when(itemInfo.getName()).thenReturn("name");
        lenient().when(ownerInfo.getId()).thenReturn(1L);
        lenient().when(itemInfo.getOwner()).thenReturn(ownerInfo);
        lenient().when(itemInfo2.getId()).thenReturn(2L);
        lenient().when(itemInfo2.getName()).thenReturn("name2");
        lenient().when(ownerInfo2.getId()).thenReturn(2L);
        lenient().when(itemInfo2.getOwner()).thenReturn(ownerInfo);
        lenient().when(itemRequestShort.getItems()).thenReturn(List.of(itemInfo, itemInfo2));

        itemRequestShort2 = mock(ItemRequestShort.class);
        ItemRequestShort.ItemInfo.OwnerInfo ownerInfo3 = mock(ItemRequestShort.ItemInfo.OwnerInfo.class);
        itemInfo3 = mock(ItemRequestShort.ItemInfo.class);
        ItemRequestShort.ItemInfo.OwnerInfo ownerInfo4 = mock(ItemRequestShort.ItemInfo.OwnerInfo.class);
        itemInfo4 = mock(ItemRequestShort.ItemInfo.class);

        lenient().when(itemRequestShort2.getId()).thenReturn(2L);
        lenient().when(itemRequestShort2.getDescription()).thenReturn("description2");
        lenient().when(itemRequestShort2.getCreated()).thenReturn(OffsetDateTime.now().plusMinutes(30));
        lenient().when(itemInfo3.getId()).thenReturn(3L);
        lenient().when(itemInfo3.getName()).thenReturn("name3");
        lenient().when(ownerInfo3.getId()).thenReturn(3L);
        lenient().when(itemInfo3.getOwner()).thenReturn(ownerInfo3);
        lenient().when(itemInfo4.getId()).thenReturn(4L);
        lenient().when(itemInfo4.getName()).thenReturn("name4");
        lenient().when(ownerInfo4.getId()).thenReturn(4L);
        lenient().when(itemInfo4.getOwner()).thenReturn(ownerInfo4);
        lenient().when(itemRequestShort2.getItems()).thenReturn(List.of(itemInfo3, itemInfo4));
    }

    @Test
    void getItemRequest() {
        when(itemRequestRepository.findItemRequestById(1L)).thenReturn(Optional.of(itemRequestShort));
        ItemRequestDto itemRequest = itemRequestService.getItemRequest(1L, 1L);

        assertEquals(1L, itemRequest.getId());
        assertEquals(itemRequestShort.getDescription(), itemRequest.getDescription());
        assertEquals(itemRequestShort.getCreated(), itemRequest.getCreated());
        assertEquals(itemRequestShort.getItems().size(), itemRequest.getItems().size());
        assertEquals(itemInfo.getId(), itemRequest.getItems().getFirst().getId());
        assertEquals(itemInfo.getName(), itemRequest.getItems().get(0).getName());
        assertEquals(itemInfo.getOwner().getId(), itemRequest.getItems().get(0).getOwnerId());
        assertEquals(itemInfo2.getId(), itemRequest.getItems().get(1).getId());
        assertEquals(itemInfo2.getName(), itemRequest.getItems().get(1).getName());
        assertEquals(itemInfo2.getOwner().getId(), itemRequest.getItems().get(1).getOwnerId());
    }

    @Test
    void getUserItemRequests() {
        when(itemRequestRepository.findItemRequestsByRequestorId(eq(1L), any(Sort.class))).thenReturn(List.of(itemRequestShort, itemRequestShort2));
        List<ItemRequestDto> itemRequests = itemRequestService.getUserItemRequests(1L);

        assertEquals(2, itemRequests.size());
        assertEquals(1L, itemRequests.getFirst().getId());
        assertEquals(itemRequestShort.getDescription(), itemRequests.getFirst().getDescription());
        assertEquals(itemRequestShort.getCreated(), itemRequests.getFirst().getCreated());
        assertEquals(itemRequestShort.getItems().size(), itemRequests.getFirst().getItems().size());
        assertEquals(itemInfo.getId(), itemRequests.getFirst().getItems().getFirst().getId());
        assertEquals(itemInfo.getName(), itemRequests.getFirst().getItems().getFirst().getName());
        assertEquals(itemInfo.getOwner().getId(), itemRequests.getFirst().getItems().get(0).getOwnerId());
        assertEquals(itemInfo2.getId(), itemRequests.getFirst().getItems().get(1).getId());
        assertEquals(itemInfo2.getName(), itemRequests.get(0).getItems().get(1).getName());
        assertEquals(itemInfo2.getOwner().getId(), itemRequests.get(0).getItems().get(1).getOwnerId());

        assertEquals(2L, itemRequests.get(1).getId());
        assertEquals(itemRequestShort2.getDescription(), itemRequests.get(1).getDescription());
        assertEquals(itemRequestShort2.getCreated(), itemRequests.get(1).getCreated());
        assertEquals(itemRequestShort2.getItems().size(), itemRequests.get(1).getItems().size());
        assertEquals(itemInfo3.getId(), itemRequests.get(1).getItems().getFirst().getId());
        assertEquals(itemInfo3.getName(), itemRequests.get(1).getItems().get(0).getName());
        assertEquals(itemInfo3.getOwner().getId(), itemRequests.get(1).getItems().get(0).getOwnerId());
        assertEquals(itemInfo4.getId(), itemRequests.get(1).getItems().get(1).getId());
        assertEquals(itemInfo4.getName(), itemRequests.get(1).getItems().get(1).getName());
        assertEquals(itemInfo4.getOwner().getId(), itemRequests.get(1).getItems().get(1).getOwnerId());
    }

    @Test
    void getAllItemRequests() {
        when(itemRequestRepository.findItemRequestByRequestorIdNot(eq(1L), any(Sort.class))).thenReturn(List.of(itemRequestShort));
        List<ItemRequestDto> itemRequests = itemRequestService.getAllItemRequests(1L);
        assertEquals(1, itemRequests.size());
        assertEquals(1L, itemRequests.getFirst().getId());
        assertEquals(itemRequestShort.getDescription(), itemRequests.getFirst().getDescription());
        assertEquals(itemRequestShort.getCreated(), itemRequests.getFirst().getCreated());
        assertEquals(itemRequestShort.getItems().size(), itemRequests.getFirst().getItems().size());
        assertEquals(itemInfo.getId(), itemRequests.getFirst().getItems().getFirst().getId());
        assertEquals(itemInfo.getName(), itemRequests.getFirst().getItems().get(0).getName());
        assertEquals(itemInfo.getOwner().getId(), itemRequests.getFirst().getItems().get(0).getOwnerId());
        assertEquals(itemInfo2.getId(), itemRequests.getFirst().getItems().get(1).getId());
        assertEquals(itemInfo2.getName(), itemRequests.getFirst().getItems().get(1).getName());
        assertEquals(itemInfo2.getOwner().getId(), itemRequests.getFirst().getItems().get(1).getOwnerId());
    }

    @Test
    void createItemRequest() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setName("Test");

        NewItemRequestRequest request = new NewItemRequestRequest("testDescr");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        itemRequest.setDescription(request.getDescription());
        itemRequest.setRequestor(user);
        itemRequest.setCreated(OffsetDateTime.now());
        itemRequest.setItems(List.of());
        when(userValidationService.isUserExistOrThrowNotFound(1L)).thenReturn(user);
        when(itemRequestRepository.save(any(ItemRequest.class))).thenReturn(itemRequest);
        ItemRequestDto newItemRequest = itemRequestService.createItemRequest(1L, request);
        assertEquals(1L, newItemRequest.getId());
        assertEquals(request.getDescription(), newItemRequest.getDescription());
        assertEquals(itemRequest.getCreated(), newItemRequest.getCreated());
        assertEquals(itemRequest.getItems().size(), newItemRequest.getItems().size());
    }



}
