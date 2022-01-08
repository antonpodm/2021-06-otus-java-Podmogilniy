package ru.otus.crm.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("emails")
@Getter
@Builder
public class Email {
    @Id
    private Long id;
    @NonNull
    private Long profileId;
    @NonNull
    private String email;

    public Email(@NonNull Long profileId, @NonNull String email) {
        this(null,profileId,email);
    }

    @PersistenceConstructor
    public Email(Long id, @NonNull Long profileId, @NonNull String email) {
        this.id = id;
        this.profileId = profileId;
        this.email = email;
    }
}
