package com.devdmitryn.userofthedaybot.controller.command;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import com.devdmitryn.userofthedaybot.controller.util.SenderMessage;
import com.devdmitryn.userofthedaybot.service.ChatService;
import com.devdmitryn.userofthedaybot.service.UserInChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.devdmitryn.userofthedaybot.controller.util.TextMessage.LOSER_MESSAGES;

@Component
@Scope("prototype")
public class FindLoserCommand extends Command {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserInChatService userInChatService;
    @Autowired
    private UserOfTheDayBot bot;

    @Override
    public void execute(Message receivedMessage) throws TelegramApiException {
        CustomChat chat = chatService.getById(receivedMessage.getChatId());
        if(chat.getLuckyTime().isBefore(LocalDateTime.now().minusSeconds(30))){
            updateLoser(chat);
        }else{
            String forbiddenMessage = "For this 30 seconds loser is " + chat.getLucky().getNotNullName();
            bot.sendMessage(chat.getId(),forbiddenMessage);
        }
    }

    private void updateLoser(CustomChat chat){
        int loserIndex = new Random().nextInt(chat.getUsers().size());
        UserInChat loser = chat.getUsers().get(loserIndex);
        loser.incrementLoserCounter();
        chat.setLoser(loser.getUser());
        chat.setLoserTime(LocalDateTime.now());
        chatService.saveOrUpdate(chat);
        userInChatService.saveOrUpdate(loser);
        sendMessages(chat, loser);
    }

    private void sendMessages(CustomChat chat, UserInChat loser){
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(LOSER_MESSAGES.length + 1);
        int i = 0;
        for (String message : LOSER_MESSAGES) {
            scheduledExecutor.schedule(new SenderMessage(chat.getId(),message,bot),i++, TimeUnit.SECONDS);
        }
        String finalMessage = loser.getUser().getNotNullName() + " is loser today!";
        scheduledExecutor.schedule(new SenderMessage(chat.getId(),finalMessage,bot),i,TimeUnit.SECONDS);
        scheduledExecutor.shutdown();
    }
}
