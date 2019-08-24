package com.devdmitryn.userofthedaybot.service.impl;

import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.CustomUser;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import com.devdmitryn.userofthedaybot.repository.UserInChatRepository;
import com.devdmitryn.userofthedaybot.service.UserInChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserInChatServiceImpl implements UserInChatService {

    private Logger logger = LoggerFactory.getLogger(UserInChatServiceImpl.class);

    @Autowired
    private UserInChatRepository userInChatRepository;
    @Override
    public void saveOrUpdate(UserInChat userInChat) {
        userInChatRepository.save(userInChat);
    }

    @Override
    public UserInChat getByChatIdAndUserId(int chatId, int userId) {
        CustomChat chat = new CustomChat();
        chat.setId(chatId);
        CustomUser user = new CustomUser();
        user.setId(userId);
        try{
            return userInChatRepository.findByChatAndUser(chat,user).get();
        }catch (NoSuchElementException e){
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(long id) {
        UserInChat userInChat = new UserInChat();
        userInChat.setId(id);
        userInChatRepository.delete(userInChat);
    }
}