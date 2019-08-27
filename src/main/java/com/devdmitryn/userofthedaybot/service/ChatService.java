package com.devdmitryn.userofthedaybot.service;

import com.devdmitryn.userofthedaybot.entity.CustomChat;

public interface ChatService {

    void saveOrUpdate(CustomChat chat);
    CustomChat getById(long id);
    void delete(long chatId);
}
