package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.bot.Bot;
import ru.otus.bot.CommandsHandler;

@Configuration
public class BeanConfig {

    @Bean
    public Bot bot(BotConfig botConfig, CommandsHandler commandsHandler) {
        return new Bot(botConfig.getName(), botConfig.getToken(), commandsHandler);
    }
}
