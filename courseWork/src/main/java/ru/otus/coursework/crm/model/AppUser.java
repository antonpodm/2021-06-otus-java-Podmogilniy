package ru.otus.coursework.crm.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("users")
@Getter
@Builder(toBuilder = true)
public class AppUser {

    @Id
    private Long id;
    @NonNull
    private Long telegramId;
    @NonNull
    private Long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    private boolean isActive;

    @PersistenceConstructor
    public AppUser(Long id, @NonNull Long telegramId, @NonNull Long chatId, String firstName, String lastName, String userName, boolean isActive) {
        this.id = id;
        this.telegramId = telegramId;
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isActive = isActive;
    }

    public AppUser(@NonNull Long telegramId, @NonNull Long chatId, String firstName, String lastName, String userName, boolean isActive) {
        this(null, telegramId, chatId, firstName, lastName, userName, isActive);
    }
}
