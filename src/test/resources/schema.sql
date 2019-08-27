DROP SCHEMA IF EXISTS telegram;

-- DROP TABLE IF EXISTS telegram.users;
-- DROP TABLE IF EXISTS chats;
-- DROP TABLE IF EXISTS chat_user;

CREATE SCHEMA telegram AUTHORIZATION sa;

CREATE TABLE telegram.users(
    id INT NOT NULL,
    username VARCHAR,
    first_name VARCHAR,
    last_name VARCHAR,
    is_bot BOOL DEFAULT FALSE,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE telegram.chats(
    id LONG NOT NULL,
    lucky_id INT,
    loser_id INT,
    lucky_time TIMESTAMP DEFAULT parsedatetime('21-06-1962 00:00:00','dd-MM-yyyy HH:mm:ss'),
    loser_time TIMESTAMP DEFAULT parsedatetime('26-12-1991 00:00:00','dd-MM-yyyy HH:mm:ss'),
    CONSTRAINT chats_pk PRIMARY KEY (id),
    CONSTRAINT lucky_fk FOREIGN KEY (lucky_id) REFERENCES telegram.users(id) ON UPDATE NO ACTION ON DELETE NO ACTION ,
    CONSTRAINT loser_fk FOREIGN KEY (loser_id) REFERENCES telegram.users(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE telegram.chat_user(
    id LONG NOT NULL IDENTITY,
    chat_id INT NOT NULL,
    user_id INT NOT NULL,
    lucky_counter INT DEFAULT 0,
    loser_counter INT DEFAULT 0,
    CONSTRAINT chat_user_pk PRIMARY KEY (id),
    CONSTRAINT chats_fk FOREIGN KEY (chat_id) REFERENCES telegram.chats(id) ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES telegram.users(id) ON UPDATE NO ACTION ON DELETE CASCADE
);

