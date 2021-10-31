package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RowDataMapperImpl<T> implements RowDataMapper<T> {

    private final EntityClassMetaData<T> classMetaData;

    public RowDataMapperImpl(EntityClassMetaData<T> classMetaData) {
        this.classMetaData = classMetaData;
    }

    @Override
    public T buildInstance(ResultSet rs) {
        try {
            var constructor = classMetaData.getConstructor();
            var parameters = constructor.getParameters();

            var constructorArgs = new HashMap<String, Integer>();
            for (int i = 0; i < parameters.length; i++) {
                constructorArgs.put(parameters[i].getName(), i);
            }

            var initArgs = new Object[constructorArgs.size()];

            var idx = 1;
            for (Field field : classMetaData.getAllFields()) {
                initArgs[constructorArgs.get(field.getName())] = rs.getObject(idx);
                idx += 1;
            }

            return constructor.newInstance(initArgs);
        } catch (Exception ex) {
            throw new RowDataMapperException("Проблема создания экземпляра класса", ex);
        }
    }

    @Override
    public List<Object> buildInsert(T t) {
        return classMetaData.getFieldsWithoutId().stream()
                .map(field -> {
                    try {
                        return field.get(t);
                    } catch (Exception e) {
                        throw new RowDataMapperException("Проблема получения значения поля", e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Object> buildUpdate(T t) {
        return classMetaData.getAllFields().stream()
                .map(field -> {
                    try {
                        return field.get(t);
                    } catch (Exception e) {
                        throw new RowDataMapperException("Проблема получения значения поля", e);
                    }
                })
                .collect(Collectors.toList());
    }

}
