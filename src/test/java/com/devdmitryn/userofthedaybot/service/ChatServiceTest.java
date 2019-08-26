package com.devdmitryn.userofthedaybot.service;

import com.devdmitryn.userofthedaybot.entity.CustomChat;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserInChatService userInChatService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private LocalDateTime defaultLuckyTime = LocalDateTime.parse("21-06-1962 00:00:00",formatter);
    private LocalDateTime defaultLoserTime =  LocalDateTime.parse("26-12-1991 00:00:00",formatter);

    @Test
    public void saveNewChatSuccessfully(){
        CustomChat chat = new CustomChat();
        chat.setId(15232);
        chatService.saveOrUpdate(chat);
        CustomChat test = chatService.getById(chat.getId());
        assertEquals(defaultLuckyTime,test.getLuckyTime());
        assertEquals(defaultLoserTime,test.getLoserTime());
    }

    @Test
    public void getExistedChat(){
        CustomChat chat = chatService.getById(1);
        assertEquals(defaultLuckyTime,chat.getLuckyTime());
        assertEquals(defaultLoserTime,chat.getLoserTime());
    }

    @Test(expected = NoSuchElementException.class)
    public void delete_thenExpectException(){
        chatService.delete(1);
        chatService.getById(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteChat_thenGetUserInChat_thenExpectException(){
        chatService.delete(1);
        userInChatService.getByChatIdAndUserId(1,1);
    }
}
