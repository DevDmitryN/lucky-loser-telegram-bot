package com.devdmitryn.userofthedaybot.controller.command;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import com.devdmitryn.userofthedaybot.service.UserInChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.devdmitryn.userofthedaybot.controller.util.TextMessage.LUCKY_STATISTIC;

@Component
@Scope("prototype")
public class LuckyStatisticCommand extends Command {

    @Autowired
    private UserInChatService userInChatService;
    @Autowired
    private UserOfTheDayBot bot;

    @Override
    public void execute(Message receivedMessage) throws TelegramApiException {
        List<UserInChat> usersInChat = userInChatService.getByChatIdOrderByLuckyCounter(receivedMessage.getChatId());

        StringBuffer text = new StringBuffer(LUCKY_STATISTIC );

        for (UserInChat userInChat : usersInChat) {
            text.append(userInChat.getUser().getNotNullName() + " - " + userInChat.getLuckyCounter() + ",\n");
        }

        SendMessage message = new SendMessage();
        message.setText(text.toString());
        message.setChatId(receivedMessage.getChatId());
        bot.execute(message);

    }
}
