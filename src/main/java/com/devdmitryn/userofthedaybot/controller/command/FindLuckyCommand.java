package com.devdmitryn.userofthedaybot.controller.command;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import com.devdmitryn.userofthedaybot.controller.util.SenderMessage;
import com.devdmitryn.userofthedaybot.service.ChatService;
import com.devdmitryn.userofthedaybot.service.UserInChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static com.devdmitryn.userofthedaybot.controller.util.TextMessage.LUCKY_MESSAGES;

@Component
@Scope("prototype")
public class FindLuckyCommand extends Command {

    private Logger logger = LoggerFactory.getLogger(FindLuckyCommand.class);

    private final int INTERVAL = 30;

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserInChatService userInChatService;
    @Autowired
    private UserOfTheDayBot bot;

    @Override
    public void execute(Message receivedMessage) throws TelegramApiException {
        CustomChat chat = chatService.getById(receivedMessage.getChatId());
        if(chat.getLuckyTime().isBefore(LocalDateTime.now().minusSeconds(INTERVAL))){
            updateLucky(chat);
        }else{
            String forbiddenMessage = "For this 30 seconds lucky is " + chat.getLucky().getNotNullName();
            bot.sendMessage(chat.getId(),forbiddenMessage);
        }
    }

    private void updateLucky(CustomChat chat){
        int luckyIndex = new Random().nextInt(chat.getUsers().size());
        UserInChat lucky = chat.getUsers().get(luckyIndex);
        lucky.incrementLuckyCounter();
        chat.setLucky(lucky.getUser());
        chat.setLuckyTime(LocalDateTime.now());
        chatService.saveOrUpdate(chat);
        userInChatService.saveOrUpdate(lucky);
        sendMessages(chat,lucky);

    }
    private void sendMessages(CustomChat chat, UserInChat luckyInChat){
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(LUCKY_MESSAGES.length + 1);
        int i = 0;
        for (String message : LUCKY_MESSAGES) {
            scheduledExecutor.schedule(new SenderMessage(chat.getId(),message,bot),i++,TimeUnit.SECONDS);
        }
        String finalMessage = luckyInChat.getUser().getNotNullName() + " is lucky today!";
        scheduledExecutor.schedule(new SenderMessage(chat.getId(),finalMessage,bot),i,TimeUnit.SECONDS);
        scheduledExecutor.shutdown();
    }
}
