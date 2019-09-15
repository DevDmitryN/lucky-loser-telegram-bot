package com.devdmitryn.userofthedaybot.repository;

import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.CustomUser;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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
        Optional<UserInChat> userInChat = userInChatRepository.findByChatIdAndUserId(1,1);
        assertTrue(userInChat.isPresent());
    }

    @Test
    public void getByChatIdOrderByLuckyCounterDescSuccessfully(){
        List<UserInChat> usersInChat = userInChatRepository.findAllByChatIdOrderByLuckyCounterDesc(1L);
        assertNotNull(usersInChat);
        assertNotEquals(0,usersInChat.size());
        assertEquals(4,usersInChat.get(0).getLuckyCounter());
        assertEquals(2,usersInChat.get(1).getLuckyCounter());
        assertEquals(1,usersInChat.get(2).getLuckyCounter());
    }

    @Test
    public void getByChatIdOrderByLoserCounterDescSuccessfully(){
        List<UserInChat> usersInChat = userInChatRepository.findAllByChatIdOrderByLoserCounterDesc(2L);
        assertNotNull(usersInChat);
        assertNotEquals(0,usersInChat.size());
        assertEquals(3,usersInChat.get(0).getLoserCounter());
        assertEquals(2,usersInChat.get(1).getLoserCounter());
    }

}
