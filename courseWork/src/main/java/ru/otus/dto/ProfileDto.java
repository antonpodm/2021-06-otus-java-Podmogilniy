package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.crm.model.Profile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDto {

    private Long id;
    private String name;

    public ProfileDto(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
    }

    public Profile toProfile() {
        return new Profile(id, name);
    }
}
