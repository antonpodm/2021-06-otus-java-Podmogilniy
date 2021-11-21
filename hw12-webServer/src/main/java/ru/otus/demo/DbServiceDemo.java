package ru.otus.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.dao.ClientDao;
import ru.otus.dao.ClientDaoImpl;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.WebServer;
import ru.otus.server.WebServerImpl;
import ru.otus.services.*;

import java.util.List;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        createClients(dbServiceClient);

        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UserAuthService authService = new UserAuthServiceImpl(userDao);

        ClientDao clientDao = new ClientDaoImpl(dbServiceClient);
        WebServer webServer = new WebServerImpl(WEB_SERVER_PORT,
                clientDao, gson, templateProcessor, authService);

        webServer.start();
        webServer.join();

    }

    private static void createClients(DbServiceClientImpl dbServiceClient) {
        Client client1 = Client.builder()
                .setName("dbServiceFirst")
                .setAddress("Address1")
                .addPhone("1 88005553535")
                .addPhone("1 89005553535")
                .addPhone("1 80005553535")
                .build();

        dbServiceClient.saveClient(client1);

        Client client2 = Client.builder()
                .setName("dbServiceSecond")
                .setAddress("Address2")
                .build();

        var clientSecond = dbServiceClient.saveClient(client2);
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);
///
        Client client2Updated = Client.builder()
                .setId(clientSecondSelected.getId())
                .setName("dbServiceSecondUpdated")
                .setAddress(/*clientSecondSelected.getAddress()*/"Address3")
                .setPhones(List.of(new Phone("3 88005553535"), new Phone("3 89005553535")))
                .build();

        dbServiceClient.saveClient(client2Updated);
        var clientUpdatedTakenFromDB = dbServiceClient.getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdatedTakenFromDB:{}", clientUpdatedTakenFromDB);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }
}
