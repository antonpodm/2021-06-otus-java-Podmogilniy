package ru.otus.protobuf.service;

import java.util.ArrayList;
import java.util.List;

public class ValuesServiceImpl implements ValuesService {

    private final List<Long> values = new ArrayList<>();

    @Override
    public List<Long> getValues(Long firstValue, Long lastValue) {
        for (long i = firstValue; i <= lastValue; i++) {
            values.add(i);
        }
        return values;
    }
}
