package ru.otus.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration("appConfig")
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String url;
    private long checkTime;
}
