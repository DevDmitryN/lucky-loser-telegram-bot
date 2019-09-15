package com.devdmitryn.userofthedaybot.controller.command;

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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.devdmitryn.userofthedaybot.controller.util.TextMessage.*;

@Component
@Scope("prototype")
public class SignUpCommand extends Command {

    private Logger logger = LoggerFactory.getLogger(SignUpCommand.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserInChatService userInChatService;
    @Autowired
    private UserOfTheDayBot bot;

    @Override
    public void execute(Message receivedMessage) throws TelegramApiException {
        SendMessage message = new SendMessage();
        User telegramUser = receivedMessage.getFrom();

        ModelMapper mapper = new ModelMapper();
        CustomUser user = mapper.map(telegramUser,CustomUser.class);

        userService.saveOfUpdate(user);

        if(userInChatService.isExist(receivedMessage.getChatId(),user.getId())){

            message.setText(user.getNotNullName() + ALREADY_IN_THE_GAME);
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
            message.setText(WELCOME_TO_THE_GAME + user.getNotNullName());
            bot.execute(message);
        }


    }
}
