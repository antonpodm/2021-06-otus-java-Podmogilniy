package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.crm.model.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDto {

    private Long id;
    private Long profileId;
    private String email;

    public EmailDto(Email email) {
        this.id = email.getId();
        this.profileId = email.getProfileId();
        this.email = email.getEmail();
    }

    public Email toEmail() {
        return new Email(id, profileId, email);
    }
}
