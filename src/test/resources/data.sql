INSERT INTO telegram.users(id,username,first_name,last_name) VALUES
    (1,'username1','first_name1','last_name1'),
    (2,'username2','first_name2','last_name2'),
    (3,'username3','first_name3','last_name3');

INSERT INTO telegram.chats(id) VALUES
    (1),(2);

INSERT INTO telegram.chat_user(chat_id,user_id) VALUES
    (1,1),(1,2),(1,3),
    (2,1),(2,3);
