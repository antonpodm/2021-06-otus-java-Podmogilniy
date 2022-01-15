package ru.otus.protobuf.service;

import java.util.List;

public interface ValuesService {

    List<Long> getValues(Long firstValue, Long lastValue);
}
