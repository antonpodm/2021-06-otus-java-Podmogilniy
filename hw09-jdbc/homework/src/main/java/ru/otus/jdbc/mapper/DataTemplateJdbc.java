package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final RowDataMapper<T> rowDataMapper;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, RowDataMapper<T> rowDataMapper) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.rowDataMapper = rowDataMapper;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return rowDataMapper.buildInstance(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var result = new ArrayList<T>();
            try {
                while (rs.next()) {
                    result.add(rowDataMapper.buildInstance(rs));
                }
                return result;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T t) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    rowDataMapper.buildInsert(t));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T t) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    rowDataMapper.buildUpdate(t));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
