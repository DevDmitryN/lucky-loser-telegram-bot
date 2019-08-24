package com.devdmitryn.userofthedaybot.service;

import com.devdmitryn.userofthedaybot.entity.CustomChat;

public interface ChatService {

    void saveOrUpdate(CustomChat chat);
    CustomChat getById(int id);
    void delete(int chatId);
}
