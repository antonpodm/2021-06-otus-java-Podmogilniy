package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.Instant;

public class ProcessorSecondsChecker implements Processor {

    private final Time time;

    public ProcessorSecondsChecker(){
        this(Instant::now);
    }

    public ProcessorSecondsChecker(Time time){
        this.time = time;
    }
    @Override
    public Message process(Message message) {
        if(time.get().getEpochSecond() % 2 == 0){
            throw new RuntimeException();
        }
        return message;
    }
}
