INSERT INTO users(name, email)
VALUES ('Alice', 'alice@example.com'),
       ('Bob', 'bob@example.com'),
       ('Soul', 'soul@example.com'),
       ('Mike', 'mike@example.com');

INSERT INTO items(name, description, is_available, user_id, request_id)
VALUES ('chair', 'simple chair', false, 2, 2),
       ('watch', 'simple watch', true, 3, 3),
       ('money', 'simple money', true, 4, 4),
       ('searchItem', 'simpleSearch', true, 4, 4),
       ('searchItem', 'searchItem', false, 4, 4),
       ('unknownItem', 'searchItem', true, 4, 4);

INSERT INTO bookings(booking_start, booking_end, item_id, booker_id, status)
VALUES (CURRENT_TIMESTAMP - INTERVAL '2 day', CURRENT_TIMESTAMP - INTERVAL '8 hour', 1, 1, 'APPROVED'),
       (CURRENT_TIMESTAMP - INTERVAL '2 day', CURRENT_TIMESTAMP - INTERVAL '8 hour', 4, 1, 'WAITING'),
       (CURRENT_TIMESTAMP - INTERVAL '2 day', CURRENT_TIMESTAMP - INTERVAL '8 hour', 5, 3, 'APPROVED');