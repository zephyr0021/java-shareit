package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@AutoConfigureMockMvc
public class ItemControllerTest {

    /**
     * TODO fix tests.
     */

    @Test
    void contextLoads() {
    }
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @BeforeEach
//    void setUp() {
//        User user1 = new User();
//        user1.setName("user1");
//        user1.setEmail("user1@email.com");
//        User user2 = new User();
//        user2.setName("user2");
//        user2.setEmail("user2@email.com");
//        User user3 = new User();
//        user3.setName("user3");
//        user3.setEmail("user3@email.com");
//
//        userRepository.createUser(user1);
//        userRepository.createUser(user2);
//        userRepository.createUser(user3);
//
//        Item item1 = new Item();
//        item1.setName("item1");
//        item1.setDescription("description1");
//        item1.setAvailable(true);
//        item1.setOwnerId(userRepository.findById(1L).get().getId());
//        Item item2 = new Item();
//        item2.setName("item2");
//        item2.setDescription("description2");
//        item2.setAvailable(false);
//        item2.setOwnerId(userRepository.findById(2L).get().getId());
//        Item item3 = new Item();
//        item3.setName("item3");
//        item3.setDescription("description3");
//        item3.setAvailable(false);
//        item3.setOwnerId(userRepository.findById(1L).get().getId());
//        Item item4 = new Item();
//        item4.setName("item4");
//        item4.setDescription("description4");
//        item4.setAvailable(true);
//        item4.setOwnerId(userRepository.findById(2L).get().getId());
//        itemRepository.createItem(item1);
//        itemRepository.createItem(item2);
//        itemRepository.createItem(item3);
//        itemRepository.createItem(item4);
//    }
//
//    @AfterEach
//    void tearDown() {
//        userRepository.clearData();
//        itemRepository.clearData();
//    }
//
//    @Test
//    void getItem() throws Exception {
//        mockMvc.perform(get("/items/2")
//                        .header("X-Sharer-User-Id", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("item2"))
//                .andExpect(jsonPath("$.description").value("description2"));
//    }
//
//    @Test
//    void getItemWithoutHeader() throws Exception {
//        mockMvc.perform(get("/items/1"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("missing required header"));
//    }
//
//    @Test
//    void getItemNotFoundUserHeader() throws Exception {
//        mockMvc.perform(get("/items/1")
//                        .header("X-Sharer-User-Id", "25"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("not found"))
//                .andExpect(jsonPath("$.message").value("X-Sharer-User-Id not found"));
//    }
//
//    @Test
//    void getAllUserItems() throws Exception {
//        mockMvc.perform(get("/items")
//                        .header("X-Sharer-User-Id", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].name").value("item1"))
//                .andExpect(jsonPath("$[1].description").value("description3"));
//    }
//
//    @Test
//    void getAllUserItemsWithoutHeader() throws Exception {
//        mockMvc.perform(get("/items"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("missing required header"));
//    }
//
//    @Test
//    void getAllUserItemsNotFoundUserHeader() throws Exception {
//        mockMvc.perform(get("/items")
//                        .header("X-Sharer-User-Id", "25"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("not found"))
//                .andExpect(jsonPath("$.message").value("X-Sharer-User-Id not found"));
//    }
//
//    @Test
//    void searchItems() throws Exception {
//        mockMvc.perform(get("/items/search?text=item1")
//                        .header("X-Sharer-User-Id", "3"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].name").value("item1"))
//                .andExpect(jsonPath("$[0].description").value("description1"));
//
//        mockMvc.perform(get("/items/search?text=item")
//                        .header("X-Sharer-User-Id", "3"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].name").value("item1"))
//                .andExpect(jsonPath("$[0].description").value("description1"))
//                .andExpect(jsonPath("$[1].name").value("item4"))
//                .andExpect(jsonPath("$[1].description").value("description4"));
//
//        mockMvc.perform(get("/items/search?text=ItEm")
//                        .header("X-Sharer-User-Id", "3"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].name").value("item1"))
//                .andExpect(jsonPath("$[0].description").value("description1"))
//                .andExpect(jsonPath("$[1].name").value("item4"))
//                .andExpect(jsonPath("$[1].description").value("description4"));
//
//
//        mockMvc.perform(get("/items/search?text=sdfegw")
//                        .header("X-Sharer-User-Id", "3"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(0));
//
//        mockMvc.perform(get("/items/search?text=")
//                        .header("X-Sharer-User-Id", "3"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(0));
//    }
//
//    @Test
//    void searchItemsWithNotFoundUserHeader() throws Exception {
//        mockMvc.perform(get("/items/search?text=item3")
//                        .header("X-Sharer-User-Id", "25"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("not found"))
//                .andExpect(jsonPath("$.message").value("X-Sharer-User-Id not found"));
//    }
//
//    @Test
//    void createItem() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item5\",\n" +
//                "  \"description\": \"descrition5\",\n" +
//                "  \"available\": true\n" +
//                "}";
//        mockMvc.perform(post("/items")
//                        .header("X-Sharer-User-Id", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(5))
//                .andExpect(jsonPath("$.name").value("item5"))
//                .andExpect(jsonPath("$.description").value("descrition5"))
//                .andExpect(jsonPath("$.available").value(true));
//
//        assertEquals("item5", itemRepository.findById(5L).get().getName());
//    }
//
//    @Test
//    void createItemWithoutHeader() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item5\",\n" +
//                "  \"description\": \"descrition5\",\n" +
//                "  \"available\": true\n" +
//                "}";
//        mockMvc.perform(post("/items")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("missing required header"));
//    }
//
//    @Test
//    void createItemWithNotFoundUserHeader() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item5\",\n" +
//                "  \"description\": \"descrition5\",\n" +
//                "  \"available\": true\n" +
//                "}";
//        mockMvc.perform(post("/items")
//                        .header("X-Sharer-User-Id", "1213")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("not found"))
//                .andExpect(jsonPath("$.message").value("X-Sharer-User-Id not found"));
//    }
//
//    @Test
//    void createItemWithoutName() throws Exception {
//        String json = "{\n" +
//                "  \"description\": \"descrition5\",\n" +
//                "  \"available\": true\n" +
//                "}";
//        mockMvc.perform(post("/items")
//                        .header("X-Sharer-User-Id", "1213")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("validation error"))
//                .andExpect(jsonPath("$.message").value("name must not be blank or null or empty"));
//    }
//
//    @Test
//    void createItemWithoutDescription() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item5\",\n" +
//                "  \"available\": true\n" +
//                "}";
//        mockMvc.perform(post("/items")
//                        .header("X-Sharer-User-Id", "1213")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("validation error"))
//                .andExpect(jsonPath("$.message").value("description must not be blank or null or empty"));
//    }
//
//    @Test
//    void createItemWithoutAvailable() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item5\",\n" +
//                "  \"description\": \"descrition5\"\n" +
//                "}";
//        mockMvc.perform(post("/items")
//                        .header("X-Sharer-User-Id", "1213")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("validation error"))
//                .andExpect(jsonPath("$.message").value("available must not be blank or null or empty"));
//    }
//
//    @Test
//    void updateItem() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item1upd\",\n" +
//                "  \"description\": \"description1upd\",\n" +
//                "  \"available\": false\n" +
//                "}";
//        mockMvc.perform(patch("/items/1")
//                        .header("X-Sharer-User-Id", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("item1upd"))
//                .andExpect(jsonPath("$.description").value("description1upd"))
//                .andExpect(jsonPath("$.available").value(false));
//
//        assertEquals("item1upd", itemRepository.findById(1L).get().getName());
//        assertEquals("description1upd", itemRepository.findById(1L).get().getDescription());
//        assertEquals(false, itemRepository.findById(1L).get().getAvailable());
//    }
//
//    @Test
//    void updateItemName() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item1upd\"\n" +
//                "}";
//        mockMvc.perform(patch("/items/1")
//                        .header("X-Sharer-User-Id", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("item1upd"))
//                .andExpect(jsonPath("$.description").value("description1"))
//                .andExpect(jsonPath("$.available").value(true));
//
//        assertEquals("item1upd", itemRepository.findById(1L).get().getName());
//        assertEquals("description1", itemRepository.findById(1L).get().getDescription());
//        assertEquals(true, itemRepository.findById(1L).get().getAvailable());
//    }
//
//    @Test
//    void updateItemDescription() throws Exception {
//        String json = "{\n" +
//                "  \"description\": \"description1upd\"\n" +
//                "}";
//        mockMvc.perform(patch("/items/1")
//                        .header("X-Sharer-User-Id", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("item1"))
//                .andExpect(jsonPath("$.description").value("description1upd"))
//                .andExpect(jsonPath("$.available").value(true));
//
//        assertEquals("item1", itemRepository.findById(1L).get().getName());
//        assertEquals("description1upd", itemRepository.findById(1L).get().getDescription());
//        assertEquals(true, itemRepository.findById(1L).get().getAvailable());
//    }
//
//    @Test
//    void updateItemAvailable() throws Exception {
//        String json = "{\n" +
//                "  \"available\": false\n" +
//                "}";
//        mockMvc.perform(patch("/items/1")
//                        .header("X-Sharer-User-Id", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("item1"))
//                .andExpect(jsonPath("$.description").value("description1"))
//                .andExpect(jsonPath("$.available").value(false));
//
//        assertEquals("item1", itemRepository.findById(1L).get().getName());
//        assertEquals("description1", itemRepository.findById(1L).get().getDescription());
//        assertEquals(false, itemRepository.findById(1L).get().getAvailable());
//    }
//
//    @Test
//    void updateItemWithoutHeader() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item1upd\",\n" +
//                "  \"description\": \"description1upd\",\n" +
//                "  \"available\": false\n" +
//                "}";
//        mockMvc.perform(patch("/items/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("missing required header"));
//    }
//
//    @Test
//    void updateItemWithNotFoundHeader() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item1upd\",\n" +
//                "  \"description\": \"description1upd\",\n" +
//                "  \"available\": false\n" +
//                "}";
//        mockMvc.perform(patch("/items/1")
//                        .header("X-Sharer-User-Id", "1324")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("not found"))
//                .andExpect(jsonPath("$.message").value("X-Sharer-User-Id not found"));
//
//    }
//
//    @Test
//    void updateUserItemNotAccessible() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"item1upd\",\n" +
//                "  \"description\": \"description1upd\",\n" +
//                "  \"available\": false\n" +
//                "}";
//        mockMvc.perform(patch("/items/1")
//                        .header("X-Sharer-User-Id", "3")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isForbidden())
//                .andExpect(jsonPath("$.error").value("access denied"))
//                .andExpect(jsonPath("$.message").value("Cannot access to this item"));
//    }




}
