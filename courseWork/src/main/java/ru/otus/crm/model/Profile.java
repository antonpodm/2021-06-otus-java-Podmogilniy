package ru.otus.crm.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("profiles")
@Getter
@Builder
public class Profile {

    @Id
    private Long id;
    @NonNull
    private String name;

    public Profile(@NonNull String name) {
        this(null, name);
    }

    @PersistenceConstructor
    public Profile(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
