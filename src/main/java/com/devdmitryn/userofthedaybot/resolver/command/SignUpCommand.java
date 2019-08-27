package com.devdmitryn.userofthedaybot.resolver.command;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.CustomUser;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import com.devdmitryn.userofthedaybot.service.UserInChatService;
import com.devdmitryn.userofthedaybot.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SignUpCommand extends Command {

    private Logger logger = LoggerFactory.getLogger(SignUpCommand.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserInChatService userInChatService;

    @Override
    public void execute(Message receivedMessage, UserOfTheDayBot bot) throws TelegramApiException {
        SendMessage message = new SendMessage();
        User telegramUser = receivedMessage.getFrom();

        ModelMapper mapper = new ModelMapper();
        CustomUser user = mapper.map(telegramUser,CustomUser.class);

        userService.saveOfUpdate(user);

        if(userInChatService.isExist(receivedMessage.getChatId(),user.getId())){

            message.setText(user.getNotNullName() + ", you are already in the game");
            message.setChatId(receivedMessage.getChatId());
            bot.execute(message);

        }else{

            CustomChat chat = new CustomChat();
            chat.setId(receivedMessage.getChatId());

            UserInChat userInChat = new UserInChat();
            userInChat.setChat(chat);
            userInChat.setUser(user);
            userInChatService.saveOrUpdate(userInChat);

            message.setChatId(receivedMessage.getChatId());
            message.setText("Welcome to the game, " + user.getNotNullName());
            bot.execute(message);
        }


    }
}
