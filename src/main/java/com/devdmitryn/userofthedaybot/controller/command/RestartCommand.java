package com.devdmitryn.userofthedaybot.controller.command;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.devdmitryn.userofthedaybot.controller.util.TextMessage.RESTART_MESSAGE;

@Component
@Scope("prototype")
public class RestartCommand extends Command {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserOfTheDayBot bot;

    @Override
    public void execute(Message receivedMessage) throws TelegramApiException {
        chatService.delete(receivedMessage.getChatId());
        CustomChat chat = new CustomChat();
        chat.setId(receivedMessage.getChatId());
        chatService.saveOrUpdate(chat);

        SendMessage message = new SendMessage();
        message.setChatId(receivedMessage.getChatId());
        message.setText(RESTART_MESSAGE);

        bot.execute(message);
    }
}
