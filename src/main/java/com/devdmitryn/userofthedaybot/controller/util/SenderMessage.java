package com.devdmitryn.userofthedaybot.controller.util;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SenderMessage implements Runnable{

    private Logger logger = LoggerFactory.getLogger(SenderMessage.class);

    private final long chatId;
    private final String message;
    private UserOfTheDayBot bot;

    public SenderMessage(long chatId, String message, UserOfTheDayBot bot) {
        this.chatId = chatId;
        this.message = message;
        this.bot = bot;
    }

    @Override
    public void run() {
        try {
            bot.sendMessage(chatId,message);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }
}
