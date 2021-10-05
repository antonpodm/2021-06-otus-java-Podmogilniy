package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorField11Field12ValueChanger implements Processor {

    @Override
    public Message process(Message message) {
        var field11 = message.getField11();
        var field12 = message.getField12();
        return message.toBuilder().field11(field12).field12(field11).build();
    }
}