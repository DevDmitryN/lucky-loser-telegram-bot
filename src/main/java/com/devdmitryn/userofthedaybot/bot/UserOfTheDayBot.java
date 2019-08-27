package com.devdmitryn.userofthedaybot.bot;

import com.devdmitryn.userofthedaybot.resolver.ActionResolver;
import com.devdmitryn.userofthedaybot.resolver.command.Command;
import com.devdmitryn.userofthedaybot.exception.NoSuchCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

import static com.devdmitryn.userofthedaybot.resolver.util.TextMessage.BOT_TOKEN;
import static com.devdmitryn.userofthedaybot.resolver.util.TextMessage.BOT_USERNAME;

@Component
public class UserOfTheDayBot extends TelegramLongPollingBot {

    private Logger logger = LoggerFactory.getLogger(UserOfTheDayBot.class);


    @Autowired
    private TelegramBotsApi telegramBotsApi;
    @Autowired
    private ActionResolver actionResolver;

    @Override
    public void onUpdateReceived(Update update) {
        logger.info(update.getMessage().getText());
        try {
            Command command = actionResolver.resolve(update.getMessage());
            if(command != null){
                command.execute(update.getMessage(),this);
            }
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        } catch (NoSuchCommandException e){
            logger.warn(e.getMessage());
        }

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
