package ru.otus.jdbc.mapper;

import ru.otus.interfaces.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final List<Field> allFields;


    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.allFields = initAllFields();
    }

    private List<Field> initAllFields() {
        var fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }
        return List.of(fields);
    }

    @Override
    public String getName() {
        return clazz.getSimpleName().toLowerCase();
    }

    /**
     * returns all args constructor
     */
    @Override
    public Constructor<T> getConstructor() {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (isAllArgsConstructor(constructor)) {
                return (Constructor<T>) constructor;
            }
        }
        return null;
    }

    private boolean isAllArgsConstructor(Constructor<?> constructor) {
        var parameters = Arrays.stream(constructor.getParameters())
                .collect(Collectors.toMap(Parameter::getName, Parameter::getType));

        for (var field : getAllFields()) {
            var parameterType = parameters.remove(field.getName());
            if (parameterType == null) {
                return false;
            }
            if (!parameterType.equals(field.getType())) {
                return false;
            }
        }

        return parameters.isEmpty();
    }

    @Override
    public Field getIdField() {
        return getAllFields()
                .stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields()
                .stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }
}
