package ru.otus.coursework.crm.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("sent_data")
@Getter
@Builder(toBuilder = true)
public class SentData {
    @Id
    private Long id;
    @NonNull
    private Long userId;
    private Long outerId;
    @NonNull
    private String message;

    @PersistenceConstructor
    public SentData(Long id, @NonNull Long userId, Long outerId, @NonNull String message) {
        this.id = id;
        this.userId = userId;
        this.outerId = outerId;
        this.message = message;
    }

    public SentData(@NonNull Long userId, Long outerId, @NonNull String message) {
        this(null, userId, outerId, message);
    }
}
