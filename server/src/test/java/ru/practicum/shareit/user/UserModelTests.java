package ru.practicum.shareit.user;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserModelTests {

    @Test
    void testEqualsAndHashCode() {
        Model<User> model = Instancio.of(User.class)
                .set(Select.field("id"), 1L)
                .toModel();
        User user = Instancio.create(model);
        User user2 = Instancio.create(model);

        assertEquals(user, user2);
        assertEquals(user.hashCode(), user2.hashCode());
    }
}
