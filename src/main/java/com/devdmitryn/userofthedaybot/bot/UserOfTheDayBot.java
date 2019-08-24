package com.devdmitryn.userofthedaybot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Component
public class UserOfTheDayBot extends TelegramLongPollingBot {

    private Logger logger = LoggerFactory.getLogger(UserOfTheDayBot.class);

    private TelegramBotsApi telegramBotsApi;

    public UserOfTheDayBot(TelegramBotsApi telegramBotsApi) {
        this.telegramBotsApi = telegramBotsApi;
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.info(update.getMessage().getText());
    }

    @Override
    public String getBotUsername() {
        return "EveryDayPidor_bot";
    }

    @Override
    public String getBotToken() {
        return "462243005:AAGa2qBwu-7xHAlQQrHoSY8iFi2Vw7j7YyI";
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
