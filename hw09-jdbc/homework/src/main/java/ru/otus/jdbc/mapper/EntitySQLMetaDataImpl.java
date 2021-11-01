package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.StringJoiner;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format(
                "SELECT %s FROM %s",
                buildAllFieldsIncludeId(),
                entityClassMetaData.getName());
    }

    @Override
    public String getSelectByFieldSql() {
        return String.format(
                "SELECT %s FROM %s WHERE %s = ?",
                buildAllFieldsIncludeId(),
                entityClassMetaData.getName(),
                getIdName());
    }

    @Override
    public String getInsertSql() {
        return String.format(
                "INSERT INTO %s (%s) VALUES (%s) RETURNING %s",
                entityClassMetaData.getName(),
                buildAllFieldsExcludeId(),
                buildAllFieldsExcludeIdValues(),
                getIdName()
        );
    }

    @Override
    public String getUpdateSql() {
        return String.format(
                "UPDATE %s SET (%s) WHERE %s = ?",
                entityClassMetaData.getName(),
                updateAllFields(),
                getIdName()
        );
    }

    private String buildAllFieldsIncludeId() {
        var joiner = new StringJoiner(", ");
        for (Field field : (Iterable<Field>) entityClassMetaData.getAllFields()) {
            joiner.add(field.getName());
        }
        return joiner.toString();
    }

    private String buildAllFieldsExcludeId() {
        var joiner = new StringJoiner(", ");
        for (Field field : (Iterable<Field>) entityClassMetaData.getFieldsWithoutId()) {
            joiner.add(field.getName());
        }
        return joiner.toString();
    }

    private String buildAllFieldsExcludeIdValues() {
        var joiner = new StringJoiner(", ");
        var fieldsSize = entityClassMetaData.getFieldsWithoutId().size();
        for (int i = 0; i < fieldsSize; i++) {
            joiner.add("?");
        }
        return joiner.toString();
    }

    private String updateAllFields() {
        var joiner = new StringJoiner("= ?, ", "", "= ?");
        for (Field field : (Iterable<Field>) entityClassMetaData.getAllFields()) {
            joiner.add(field.getName());
        }
        return joiner.toString();
    }

    private String getIdName() {
        return entityClassMetaData.getIdField().getName();
    }

}
