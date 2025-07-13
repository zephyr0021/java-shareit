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

