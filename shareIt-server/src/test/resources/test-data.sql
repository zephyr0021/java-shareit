INSERT INTO users(name, email)
VALUES ('Alice', 'alice@example.com');
INSERT INTO users(name, email)
VALUES ('Bob', 'bob@example.com');
INSERT INTO users(name, email)
VALUES ('Soul', 'soul@example.com');
INSERT INTO users(name, email)
VALUES ('Mike', 'mike@example.com');

INSERT INTO items(name, description, is_available, user_id, request_id)
VALUES ('chair', 'simple chair', false, 2, 2);
INSERT INTO items(name, description, is_available, user_id, request_id)
VALUES ('watch', 'simple watch', true, 3, 3);
INSERT INTO items(name, description, is_available, user_id, request_id)
VALUES ('money', 'simple money', true, 4, 4);
INSERT INTO items(name, description, is_available, user_id, request_id)
VALUES ('searchItem', 'simpleSearch', true, 4, 4);
INSERT INTO items(name, description, is_available, user_id, request_id)
VALUES ('searchItem', 'searchItem', false, 4, 4);
INSERT INTO items(name, description, is_available, user_id, request_id)
VALUES ('unknownItem', 'searchItem', true, 4, 4);

INSERT INTO bookings(booking_start, booking_end, item_id, booker_id, status)
VALUES (DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -8, CURRENT_TIMESTAMP), 1, 1, 'APPROVED');
INSERT INTO bookings(booking_start, booking_end, item_id, booker_id, status)
VALUES (DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -8, CURRENT_TIMESTAMP), 4, 1, 'WAITING');
INSERT INTO bookings(booking_start, booking_end, item_id, booker_id, status)
VALUES (DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -8, CURRENT_TIMESTAMP), 5, 3, 'APPROVED');