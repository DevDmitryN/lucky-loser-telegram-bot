package com.devdmitryn.userofthedaybot.controller.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class Command {

    public abstract void execute(Message receivedMessage) throws TelegramApiException,InterruptedException;
}
