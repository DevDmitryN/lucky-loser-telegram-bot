package com.devdmitryn.userofthedaybot.bot;

import com.devdmitryn.userofthedaybot.controller.MessageController;
import com.devdmitryn.userofthedaybot.controller.command.Command;
import com.devdmitryn.userofthedaybot.exception.NoSuchCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

import static com.devdmitryn.userofthedaybot.controller.util.TextMessage.BOT_TOKEN;
import static com.devdmitryn.userofthedaybot.controller.util.TextMessage.BOT_USERNAME;

@Component
public class UserOfTheDayBot extends TelegramLongPollingBot {

    private Logger logger = LoggerFactory.getLogger(UserOfTheDayBot.class);

    @Autowired
    private TelegramBotsApi telegramBotsApi;
    @Autowired
    private MessageController messageController;

    @Override
    public void onUpdateReceived(Update update) {
        logger.info(update.getMessage().getText());
        try {
            Command command = messageController.process(update.getMessage());
            if(command != null){
                command.execute(update.getMessage());
            }
        } catch (TelegramApiException | InterruptedException e) {
            logger.error(e.getMessage());
        } catch (NoSuchCommandException e){
            logger.warn(e.getMessage());
        }

    }

    public synchronized void sendMessage(long chatId, String text) throws TelegramApiException{
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        execute(message);
    }


    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @PostConstruct
    public void registry(){
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            logger.error("Exception to registry the bot");
            logger.error(e.getApiResponse());
        }
    }
}
