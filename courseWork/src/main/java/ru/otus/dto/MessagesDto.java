package ru.otus.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MessagesDto {

    private final List<String> messages;

    public MessagesDto(List<String> messages) {
        this.messages = new ArrayList<>();
        this.messages.addAll(messages);
    }

    @Override
    public String toString(){
        var joiner = new StringJoiner("\n");
        for(var message: messages){
            joiner.add(message);
        }
        return joiner.toString();
    }
}
