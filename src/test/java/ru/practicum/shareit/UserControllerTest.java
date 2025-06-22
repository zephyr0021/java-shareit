package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@AutoConfigureMockMvc
public class UserControllerTest {

    /**
     * TODO fix tests.
     */

    @Test
    void contextLoads() {
    }

//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        NewUserRequest user1 = new NewUserRequest();
//        NewUserRequest user2 = new NewUserRequest();
//        NewUserRequest user3 = new NewUserRequest();
//        user1.setName("test1");
//        user2.setName("test2");
//        user3.setName("test3");
//        user1.setEmail("test1@test.com");
//        user2.setEmail("test2@test.com");
//        user3.setEmail("test3@test.com");
//        userService.createUser(user1);
//        userService.createUser(user2);
//        userService.createUser(user3);
//    }
//
//    @AfterEach
//    void tearDown() {
//        userService.clearData();
//    }
//
//    @Test
//    void getAllUsers() throws Exception {
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("test1"))
//                .andExpect(jsonPath("$[0].email").value("test1@test.com"))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].name").value("test2"))
//                .andExpect(jsonPath("$[1].email").value("test2@test.com"));
//    }
//
//    @Test
//    void getUserById() throws Exception {
//        mockMvc.perform(get("/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("test1"))
//                .andExpect(jsonPath("$.email").value("test1@test.com"));
//    }
//
//    @Test
//    void createUser() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"Test User\",\n" +
//                "  \"email\": \"mail@mail.ru\"\n" +
//                "}";
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(4))
//                .andExpect(jsonPath("$.name").value("Test User"))
//                .andExpect(jsonPath("$.email").value("mail@mail.ru"));
//        UserDto createdUser = userService.getUserById(4L);
//        assertEquals("Test User", createdUser.getName());
//        assertEquals("mail@mail.ru", createdUser.getEmail());
//    }
//
//    @Test
//    void updateUser() throws Exception {
//        String jsonUpdName = "{\n" +
//                "  \"name\": \"Test User\"\n" +
//                "}";
//
//        String jsonUpdEmail = "{\n" +
//                "  \"email\": \"mail@mail.ru\"\n" +
//                "}";
//
//        String json = "{\n" +
//                "  \"name\": \"Test User\",\n" +
//                "  \"email\": \"mail3@mail.ru\"\n" +
//                "}";
//
//        mockMvc.perform(patch("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonUpdName))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Test User"))
//                .andExpect(jsonPath("$.email").value("test1@test.com"));
//        assertEquals("Test User", userService.getUserById(1L).getName());
//
//        mockMvc.perform(patch("/users/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonUpdEmail))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("test2"))
//                .andExpect(jsonPath("$.email").value("mail@mail.ru"));
//        assertEquals("mail@mail.ru", userService.getUserById(2L).getEmail());
//
//        mockMvc.perform(patch("/users/3")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Test User"))
//                .andExpect(jsonPath("$.email").value("mail3@mail.ru"));
//        assertEquals("Test User", userService.getUserById(3L).getName());
//        assertEquals("mail3@mail.ru", userService.getUserById(3L).getEmail());
//    }
//
//    @Test
//    void deleteUser() throws Exception {
//        mockMvc.perform(delete("/users/1"))
//                .andExpect(status().isOk());
//
//        assertEquals(2, userService.getAllUsers().size());
//        assertThrows(NotFoundException.class, () -> userService.getUserById(1L));
//    }
//
//    @Test
//    void createUserWithoutName() throws Exception {
//        String json = "{\n" +
//                "  \"email\": \"mail3@mail.ru\"\n" +
//                "}";
//        mockMvc.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("validation error"))
//                .andExpect(jsonPath("$.message").value("name must not be blank or null or empty"));
//    }
//
//    @Test
//    void createUserWithoutEmail() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"Test User\"\n" +
//                "}";
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("validation error"))
//                .andExpect(jsonPath("$.message").value("email must not be blank or null or empty"));
//    }
//
//    @Test
//    void createUserWithInvalidEmail() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"Test User\",\n" +
//                "  \"email\": \"mail3\"\n" +
//                "}";
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("validation error"))
//                .andExpect(jsonPath("$.message").value("email must be a well-formed email address"));
//    }
//
//    @Test
//    void createExistingUser() throws Exception {
//        String json = "{\n" +
//                "  \"name\": \"Test User\",\n" +
//                "  \"email\": \"test1@test.com\"\n" +
//                "}";
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.error").value("conflict data"))
//                .andExpect(jsonPath("$.message").value("User test1@test.com already exists"));
//    }
//
//    @Test
//    void updateUserWithInvalidEmail() throws Exception {
//        String json = "{\n" +
//                "  \"email\": \"mail3\"\n" +
//                "}";
//        mockMvc.perform(patch("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error").value("validation error"))
//                .andExpect(jsonPath("$.message").value("email must be a well-formed email address"));
//    }
//
//    @Test
//    void updateUserEmailExisting() throws Exception {
//        String json = "{\n" +
//                "  \"email\": \"test2@test.com\"\n" +
//                "}";
//        mockMvc.perform(patch("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.error").value("conflict data"))
//                .andExpect(jsonPath("$.message").value("User test2@test.com already exists"));
//    }
//

}
