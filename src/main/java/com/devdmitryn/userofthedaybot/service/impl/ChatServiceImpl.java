package com.devdmitryn.userofthedaybot.service.impl;

import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.repository.ChatRepository;
import com.devdmitryn.userofthedaybot.service.ChatService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ChatServiceImpl implements ChatService {

    private Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public void saveOrUpdate(CustomChat chat) {
        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public CustomChat getById(long id) {
        CustomChat chat = chatRepository.findById(id).get();
        Hibernate.initialize(chat.getUsers());
        return chat;
    }

    @Override
    public void delete(long chatId) {
        CustomChat chat = new CustomChat();
        chat.setId(chatId);
        chatRepository.delete(chat);
    }

    @Override
    public boolean isAllowedToUpdateLucky(CustomChat chat) {
        return false;
    }

    @Override
    public boolean isAllowedToUpdateLoser(CustomChat chat) {
        return false;
    }
}
