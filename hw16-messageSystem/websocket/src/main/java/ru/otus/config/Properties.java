package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class Properties {

    private String frontendServiceClientName = "frontendService";
    private String databaseServiceClientName = "databaseService";

    public String getFrontendServiceClientName() {
        return frontendServiceClientName;
    }

    public void setFrontendServiceClientName(String frontendServiceClientName) {
        this.frontendServiceClientName = frontendServiceClientName;
    }

    public String getDatabaseServiceClientName() {
        return databaseServiceClientName;
    }

    public void setDatabaseServiceClientName(String databaseServiceClientName) {
        this.databaseServiceClientName = databaseServiceClientName;
    }
}
