package ru.otus.jdbc.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface RowDataMapper<T> {

    T buildInstance(ResultSet rs);

    List<Object> buildInsert(T t);

    List<Object> buildUpdate(T t);
}
