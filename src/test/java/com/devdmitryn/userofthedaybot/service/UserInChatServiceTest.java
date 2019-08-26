package com.devdmitryn.userofthedaybot.service;

import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.CustomUser;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInChatServiceTest {

    @Autowired
    private UserInChatService userInChatService;

    @Test
    public void saveUserInChatSuccessfully(){
        CustomUser user = new CustomUser();
        user.setId(2);
        CustomChat chat = new CustomChat();
        chat.setId(2);
        UserInChat userInChat = new UserInChat();
        userInChat.setUser(user);
        userInChat.setChat(chat);
        userInChatService.saveOrUpdate(userInChat);
        userInChatService.getByChatIdAndUserId(2,2);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteUserInChatById_thenExpectException(){
        // userInChat with id = 2 has chat_id = 1 and user_id = 1
        userInChatService.delete(1);
        userInChatService.getByChatIdAndUserId(1,1);
    }
}
