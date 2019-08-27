package com.devdmitryn.userofthedaybot.repository;

import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.CustomUser;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInChatRepositoryTest {

    @Autowired
    private UserInChatRepository userInChatRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    private UserInChat userInChat;


    @Test
    public void saveSuccessfully(){
        assertNotEquals(0L, saveUserInChat(1,1));
        assertNotEquals(0L, saveUserInChat(2,1));
        assertNotEquals(0L, saveUserInChat(3,1));
    }


    public UserInChat saveUserInChat(int userId,long chatId){
        CustomChat chat = new CustomChat();
        chat.setId(chatId);
        CustomUser user = new CustomUser();
        user.setId(userId);
        UserInChat userInChat = new UserInChat();
        userInChat.setChat(chat);
        userInChat.setUser(user);
        return userInChatRepository.save(userInChat);
    }

    @Test
    public void getByChatAndUserSuccessfully(){
//        CustomChat chat = new CustomChat();
//        chat.setId(1);
//        CustomUser user = new CustomUser();
//        user.setId(1);
//        Optional<UserInChat> userInChat = userInChatRepository.findByChatAndUser(chat,user);
        Optional<UserInChat> userInChat = userInChatRepository.findByChatIdAndUserId(1,1);
        assertTrue(userInChat.isPresent());
    }

}
