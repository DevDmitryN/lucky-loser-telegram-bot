package com.devdmitryn.userofthedaybot.resolver;

import com.devdmitryn.userofthedaybot.resolver.util.TextMessage;
import com.devdmitryn.userofthedaybot.resolver.command.Command;
import com.devdmitryn.userofthedaybot.resolver.command.SignUpCommand;
import com.devdmitryn.userofthedaybot.resolver.command.StartCommand;
import com.devdmitryn.userofthedaybot.exception.NoSuchCommandException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ActionResolver {

    private SignUpCommand signUpCommand;
    private StartCommand startCommand;

    public ActionResolver(SignUpCommand signUpCommand, StartCommand startCommand) {
        this.signUpCommand = signUpCommand;
        this.startCommand = startCommand;
    }

    public Command resolve(Message message) throws NoSuchCommandException{
        if(message.getText().startsWith("/") && message.getText().contains("@" + TextMessage.BOT_USERNAME)){
            String text = message.getText().replace("/", "").replace("@" + TextMessage.BOT_USERNAME, "").toUpperCase();
            try {
                switch (Action.valueOf(text)) {
                    case SIGN_UP:
                        return signUpCommand;
                    case START:
                        return startCommand;
                }
            }catch (Exception e){
                throw new NoSuchCommandException(text + " is not a command");
            }
        }
        return null;
    }
}
