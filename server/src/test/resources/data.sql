INSERT INTO users(name, email)
VALUES ('Alice', 'alice@example.com'),
       ('Bob', 'bob@example.com'),
       ('Soul', 'soul@example.com'),
       ('Mike', 'mike@example.com');

INSERT INTO requests(description, created, requestor_id)
VALUES ('request 2', NOW(), 2),
       ('request 3', NOW(), 3),
       ('request 4', NOW(), 4),
       ('request 5', NOW(), 3);

INSERT INTO items(name, description, is_available, user_id, request_id)
VALUES ('chair', 'simple chair', false, 2, 2),
       ('watch', 'simple watch', true, 3, 3),
       ('money', 'simple money', true, 4, 4),
       ('searchItem', 'simpleSearch', true, 4, 4),
       ('searchItem', 'searchItem', false, 4, 4),
       ('unknownItem', 'searchItem', true, 4, 4);

INSERT INTO bookings(booking_start, booking_end, item_id, booker_id, status)
VALUES (TIMESTAMPADD(HOUR, -48, CURRENT_TIMESTAMP),
        TIMESTAMPADD(HOUR, -8, CURRENT_TIMESTAMP),
        1, 1, 'APPROVED'),
       (TIMESTAMPADD(HOUR, -48, CURRENT_TIMESTAMP),
        TIMESTAMPADD(HOUR, -8, CURRENT_TIMESTAMP),
        4, 1, 'WAITING'),
       (TIMESTAMPADD(HOUR, -48, CURRENT_TIMESTAMP),
        TIMESTAMPADD(HOUR, -8, CURRENT_TIMESTAMP),
        5, 3, 'APPROVED');