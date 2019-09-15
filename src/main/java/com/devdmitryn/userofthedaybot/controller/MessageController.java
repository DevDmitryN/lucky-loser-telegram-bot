package com.devdmitryn.userofthedaybot.controller;

import com.devdmitryn.userofthedaybot.controller.command.*;
import com.devdmitryn.userofthedaybot.controller.util.TextMessage;
import com.devdmitryn.userofthedaybot.exception.NoSuchCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageController {

    @Autowired
    private ApplicationContext applicationContext;

    public Command process(Message message) throws NoSuchCommandException{
        if(message.getText().endsWith("@" + TextMessage.BOT_USERNAME) && message.getText().startsWith("/")){
            String text = message.getText().replace("/", "").replace("@" + TextMessage.BOT_USERNAME, "").toUpperCase();
            try {
                switch (Action.valueOf(text)) {
                    case SIGN_UP:
                        return applicationContext.getBean(SignUpCommand.class);
                    case START:
                        return applicationContext.getBean(StartCommand.class);
                    case QUIT:
                        return applicationContext.getBean(QuitCommand.class);
                    case RESTART:
                        return applicationContext.getBean(RestartCommand.class);
                    case LUCKY_STATISTIC:
                        return applicationContext.getBean(LuckyStatisticCommand.class);
                    case LOSER_STATISTIC:
                        return applicationContext.getBean(LoserStatisticCommand.class);
                    case FIND_LUCKY:
                        return applicationContext.getBean(FindLuckyCommand.class);
                    case FIND_LOSER:
                        return applicationContext.getBean(FindLoserCommand.class);
                }
            }catch (Exception e) {
                throw new NoSuchCommandException(text + " is not a command");
            }
        }
        return null;
    }
}
