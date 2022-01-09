package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDto {

    private Long id;
    private String name;

   /* public ProfileDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public User toProfile() {
        return new User(id, name);
    }*/
}
