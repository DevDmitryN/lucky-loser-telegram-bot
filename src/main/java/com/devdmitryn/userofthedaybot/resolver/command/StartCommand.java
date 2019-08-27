package com.devdmitryn.userofthedaybot.resolver.command;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartCommand extends Command {

    @Autowired
    private ChatService chatService;

    @Override
    public void execute(Message receivedMessage, UserOfTheDayBot bot) throws TelegramApiException {
        CustomChat chat = new CustomChat();
        chat.setId(receivedMessage.getChatId());
        chatService.saveOrUpdate(chat);
    }
}
