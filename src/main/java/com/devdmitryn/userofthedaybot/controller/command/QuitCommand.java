package com.devdmitryn.userofthedaybot.controller.command;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import com.devdmitryn.userofthedaybot.service.UserInChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.devdmitryn.userofthedaybot.controller.util.TextMessage.*;

@Component
@Scope("prototype")
public class QuitCommand extends Command {

    @Autowired
    private UserInChatService userInChatService;
    @Autowired
    private UserOfTheDayBot bot;

    @Override
    public void execute(Message receivedMessage) throws TelegramApiException {
        Chat chat = receivedMessage.getChat();
        User user = receivedMessage.getFrom();

        SendMessage message = new SendMessage();

        if(userInChatService.isExist(chat.getId(),user.getId())){
            UserInChat userInChat = userInChatService.getByChatIdAndUserId(chat.getId(),user.getId());
            userInChatService.delete(userInChat.getId());
            message.setText(SUCCESSFUL_QUIT);
            message.setChatId(chat.getId());
            bot.execute(message);
        }else{
            message.setText(FAILED_QUIT);
            message.setChatId(chat.getId());
            bot.execute(message);
        }
    }
}
