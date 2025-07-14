package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentModelTests {
    @Test
    void testEqualsAndHashCode() {
        OffsetDateTime now = OffsetDateTime.now();
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setItem(new Item());
        comment.setAuthor(new User());
        comment.setCreated(now);
        comment.setText("testComment1");

        Comment comment2 = new Comment();
        comment2.setId(1L);
        comment2.setItem(new Item());
        comment2.setAuthor(new User());
        comment2.setCreated(now.plusDays(1L));
        comment2.setText("testComment2");

        assertEquals(comment, comment2);
        assertEquals(comment.hashCode(), comment2.hashCode());
    }
}
