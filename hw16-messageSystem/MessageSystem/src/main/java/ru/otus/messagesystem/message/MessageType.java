package ru.otus.messagesystem.message;

public enum MessageType {
    VOID_MESSAGE("voidMessage"),
    USER_DATA("UserData"),
    GET_CLIENTS("getClients"),
    SAVE_CLIENT("saveClient")
    ;

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
