package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.cachehw.MyKey;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {

        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl<Client>(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        RowDataMapper rowDataMapperClient = new RowDataMapperImpl<Client>(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient, rowDataMapperClient);

        //actionsWithoutCache(transactionRunner, dataTemplateClient);
        actionsWithCache(transactionRunner, dataTemplateClient);

    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }

    private static void actionsWithoutCache(TransactionRunner tr, DataTemplateJdbc dt) {
        doAction(new DbServiceClientImpl(tr, dt));
    }

    private static void actionsWithCache(TransactionRunner tr, DataTemplateJdbc dt) {
        var myCache = new MyCache<MyKey<Long>, Client>();

        var listener = new HwListener<MyKey<Long>, Client>() {
            @Override
            public void notify(MyKey<Long> key, Client value, String action) {
                log.info("key:{}, value:{}, action: {}", key.get(), value, action);
            }
        };

        myCache.addListener(listener);

        doAction(new DbServiceClientImpl(tr, dt, myCache));
    }

    private static void doAction(DBServiceClient dbServiceClient) {
        var startTime = System.currentTimeMillis();

        var ids = createClients(dbServiceClient, 5000);
        getClientsOneByOne(dbServiceClient, ids);
        getClientsOneByOne(dbServiceClient, ids);
        //System.gc();
        getClientsOneByOne(dbServiceClient, ids);

        log.info("full workTime:{}", (System.currentTimeMillis() - startTime));
    }

    private static List<Long> createClients(DBServiceClient dbServiceClient, int amount) {
        var ids = new ArrayList<Long>();
        var startTime = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            var client = dbServiceClient.saveClient(new Client("name: " + i));
            ids.add(client.getId());
        }
        log.info("full saving time:{}", (System.currentTimeMillis() - startTime));
        return ids;
    }

    private static void getClientsOneByOne(DBServiceClient dbServiceClient, List<Long> ids) {
        var startTime = System.currentTimeMillis();
        for (Long id : ids) {
            dbServiceClient.getClient(id);
        }
        log.info("full loading time:{}", (System.currentTimeMillis() - startTime));
    }
}
