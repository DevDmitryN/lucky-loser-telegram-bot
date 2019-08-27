package com.devdmitryn.userofthedaybot.service;

import com.devdmitryn.userofthedaybot.entity.UserInChat;

public interface UserInChatService {
    void saveOrUpdate(UserInChat userInChat);
    UserInChat getByChatIdAndUserId(long chatId,int userId);
    void delete(long id);
    boolean isExist(long chatId, int userId);
}
