package com.devdmitryn.userofthedaybot.resolver.command;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class Command {

    public abstract void execute(Message receivedMessage, UserOfTheDayBot bot) throws TelegramApiException;
}
