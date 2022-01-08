package ru.otus.toolbox;

import org.jetbrains.annotations.NotNull;
import ru.otus.crm.model.Email;
import ru.otus.crm.model.Good;
import ru.otus.crm.model.Profile;
import ru.otus.dto.EmailDto;
import ru.otus.dto.GoodDto;
import ru.otus.dto.ProfileDto;

import java.util.List;

public class RestControllerHelper {

    public static List<ProfileDto> makeProfilesDto(@NotNull List<Profile> profiles) {
        return profiles
                .stream()
                .map(ProfileDto::new)
                .toList();
    }

    public static List<EmailDto> makeEmailsDto(@NotNull List<Email> emails) {
        return emails
                .stream()
                .map(EmailDto::new)
                .toList();
    }

    public static List<GoodDto> makeGoodsDto(@NotNull List<Good> goods) {
        return goods
                .stream()
                .map(GoodDto::new)
                .toList();
    }

}
