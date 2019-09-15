DROP TABLE IF EXISTS telegram.chat_user;
DROP TABLE IF EXISTS telegram.chats;
DROP TABLE IF EXISTS telegram.users;
DROP SCHEMA IF EXISTS telegram;

CREATE SCHEMA telegram AUTHORIZATION postgres;

CREATE TABLE telegram.users(
    id INT4 NOT NULL,
    username VARCHAR,
    first_name VARCHAR,
    last_name VARCHAR,
    is_bot BOOL DEFAULT FALSE,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE telegram.chats(
    id INT8 NOT NULL,
    lucky_id INT4,
    loser_id INT4,
    lucky_time TIMESTAMP DEFAULT TO_TIMESTAMP('21-06-1962 00:00:00', 'DD-MM-YYYY HH24:MI:SS'),
    loser_time TIMESTAMP DEFAULT TO_TIMESTAMP('26-12-1991 00:00:00','DD-MM-YYYY HH24:MI:SS'),
    CONSTRAINT chats_pk PRIMARY KEY (id),
    CONSTRAINT lucky_fk FOREIGN KEY (lucky_id) REFERENCES telegram.users(id) ON UPDATE NO ACTION ON DELETE NO ACTION ,
    CONSTRAINT loser_fk FOREIGN KEY (loser_id) REFERENCES telegram.users(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE telegram.chat_user(
    id INT8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    chat_id INT8 NOT NULL,
    user_id INT4 NOT NULL,
    lucky_counter INT4 DEFAULT 0,
    loser_counter INT4 DEFAULT 0,
    CONSTRAINT chat_user_pk PRIMARY KEY (id),
    CONSTRAINT chats_fk FOREIGN KEY (chat_id) REFERENCES telegram.chats(id) ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES telegram.users(id) ON UPDATE NO ACTION ON DELETE CASCADE
);