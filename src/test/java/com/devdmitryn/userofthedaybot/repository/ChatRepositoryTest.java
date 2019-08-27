package com.devdmitryn.userofthedaybot.repository;

import com.devdmitryn.userofthedaybot.entity.CustomChat;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    private CustomChat chat;

    @Before
    public void createChat(){
        chat = new CustomChat();
        chat.setId(1L);
    }

    @Test
    public void saveChatSuccessfully(){
        chatRepository.save(chat);
        Optional<CustomChat> chatFromDb = chatRepository.findById(chat.getId());
        assertTrue(chatFromDb.isPresent());
        assertEquals(chat.getId(),chatFromDb.get().getId());
    }

    @Test
    public void deleteChatSuccessfully(){
        chatRepository.delete(chat);
        Optional<CustomChat> deletedChat = chatRepository.findById(chat.getId());
        assertFalse(deletedChat.isPresent());
    }

    @Test
    @Transactional
    public void getUsersInChatSuccessfully(){
        Optional<CustomChat> chat = chatRepository.findById(this.chat.getId());
        assertTrue(chat.isPresent());
        Hibernate.initialize(chat.get().getUsers());
        assertNotNull(chat.get().getUsers());
        chat.get().getUsers().forEach(u -> assertNotNull(u));
    }
}
