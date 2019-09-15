package com.devdmitryn.userofthedaybot.service;

import com.devdmitryn.userofthedaybot.entity.UserInChat;

import java.util.List;

public interface UserInChatService {
    void saveOrUpdate(UserInChat userInChat);
    UserInChat getByChatIdAndUserId(long chatId,int userId);
    void delete(long id);
    boolean isExist(long chatId, int userId);
    List<UserInChat> getByChatIdOrderByLuckyCounter(long chatId);
    List<UserInChat> getByChatIdOrderByLoserCounter(long chatId);
}
