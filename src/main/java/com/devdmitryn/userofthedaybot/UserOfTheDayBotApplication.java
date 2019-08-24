package com.devdmitryn.userofthedaybot;

import com.devdmitryn.userofthedaybot.bot.UserOfTheDayBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UserOfTheDayBotApplication {


    public static void main(String[] args) {
        SpringApplication.run(UserOfTheDayBotApplication.class, args);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(){
        return new TelegramBotsApi();
    }

    @PostConstruct
    public void init() {
        ApiContextInitializer.init();
    }

}
