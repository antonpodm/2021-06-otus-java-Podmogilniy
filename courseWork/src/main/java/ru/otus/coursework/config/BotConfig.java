package ru.otus.coursework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration("botConfig")
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    private String name;
    private String token;
}
