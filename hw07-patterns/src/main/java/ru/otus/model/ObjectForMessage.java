package ru.otus.model;

import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public ObjectForMessage() {

    }

    public ObjectForMessage(ObjectForMessage foreign) {
        if (foreign != null && foreign.getData() != null) {
            this.data = List.copyOf(foreign.getData());
        }
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
