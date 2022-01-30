package ru.otus.coursework.toolbox;

import org.telegram.telegrambots.meta.api.objects.User;

public class BotHelper {

    public static String makeUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() : String.format("%s %s", user.getLastName(), user.getFirstName());
    }
}
