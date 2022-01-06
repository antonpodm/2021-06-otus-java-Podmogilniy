package ru.otus.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.db.handlers.GetClientsDataRequestHandler;
import ru.otus.db.handlers.SaveClientDataRequestHandler;
import ru.otus.front.handlers.GetClientsDataResponseHandler;
import ru.otus.messagesystem.*;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@Configuration
public class AppConfig {

    public static final String FRONTEND_MS_CLIENT = "frontendMsClient";
    public static final String DATABASE_MS_CLIENT = "databaseMsClient";

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public HandlersStore handlersStore() {
        return new HandlersStoreImpl();
    }

    @Bean(DATABASE_MS_CLIENT)
    public MsClient databaseMsClient(MessageSystem messageSystem, HandlersStore handlersStore, DBServiceClient dbServiceClient, Properties properties) {
        handlersStore.addHandler(MessageType.GET_CLIENTS, new GetClientsDataRequestHandler(dbServiceClient));
        handlersStore.addHandler(MessageType.SAVE_CLIENT, new SaveClientDataRequestHandler(dbServiceClient));
        MsClient databaseMsClient = new MsClientImpl(properties.getDatabaseServiceClientName(), messageSystem, handlersStore);
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean(FRONTEND_MS_CLIENT)
    public MsClient frontendMsClient(MessageSystem messageSystem, HandlersStore handlersStore, Properties properties) {
        RequestHandler requestHandler = new GetClientsDataResponseHandler();
        handlersStore.addHandler(MessageType.GET_CLIENTS, requestHandler);
        handlersStore.addHandler(MessageType.SAVE_CLIENT, requestHandler);
        MsClient frontendMsClient = new MsClientImpl(properties.getFrontendServiceClientName(), messageSystem, handlersStore);
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }

}
