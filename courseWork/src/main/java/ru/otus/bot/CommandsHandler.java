package ru.otus.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.otus.bot.parser.ParsedAddCommand;
import ru.otus.bot.parser.ParsedRemoveCommand;
import ru.otus.crm.model.AppUser;
import ru.otus.crm.model.Good;
import ru.otus.crm.service.DBServiceGood;
import ru.otus.crm.service.DBServiceAppUser;
import ru.otus.exceptions.AppUserNotFoundException;
import ru.otus.exceptions.CommandAlreadyDoneException;
import ru.otus.exceptions.GoodNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandsHandler {

    private final DBServiceGood dbServiceGood;
    private final DBServiceAppUser dbServiceAppUser;

    public void handleStartCommand(User tgUser) {
        var userOptional = findUser(tgUser);
        AppUser.AppUserBuilder builder;

        if (userOptional.isPresent()) {
            var appUser = userOptional.get();
            if (appUser.isActive()) {
                throw new CommandAlreadyDoneException(CommandsList.START);
            }
            builder = appUser.toBuilder();
        } else {
            builder = AppUser.builder().telegramId(tgUser.getId());
        }

        var appUser = builder.firstName(tgUser.getFirstName())
                .lastName(tgUser.getLastName())
                .userName(tgUser.getUserName())
                .isActive(true).build();
        dbServiceAppUser.save(appUser);
    }

    public void handleStopCommand(User tgUser) {
        var userOptional = findUser(tgUser);
        if (userOptional.isEmpty()) {
            throw new AppUserNotFoundException(tgUser.getId());
        } else {
            var appUser = userOptional.get();
            if (!appUser.isActive()) {
                throw new CommandAlreadyDoneException(CommandsList.STOP);
            }
            appUser = appUser.toBuilder().isActive(false).build();
            dbServiceAppUser.save(appUser);
        }
    }

    public void handleAddCommand(User tgUser, String[] strings) {
        var userOptional = findUser(tgUser);
        if (userOptional.isEmpty()) {
            throw new AppUserNotFoundException(tgUser.getId());
        } else {
            var appUser = userOptional.get();
            var parsedCommand = new ParsedAddCommand(strings);
            var existingGood = dbServiceGood.findByUserIdAndOuterId(appUser.getId(), parsedCommand.getOuterId());
            Good good;
            if (existingGood.isPresent()) {
                good = existingGood.get().updateFromCommand(parsedCommand);
            } else {
                good = Good.builder().userId(appUser.getId()).build().updateFromCommand(parsedCommand);
            }
            dbServiceGood.save(good);
        }
    }

    public void handleRemoveCommand(User tgUser, String[] strings) {
        var userOptional = findUser(tgUser);
        if (userOptional.isEmpty()) {
            throw new AppUserNotFoundException(tgUser.getId());
        } else {
            var appUser = userOptional.get();
            var parsedCommand = new ParsedRemoveCommand(strings);
            var existingGood = dbServiceGood.findByUserIdAndOuterId(appUser.getId(), parsedCommand.getOuterId());
            if (existingGood.isEmpty()) {
                throw new GoodNotFoundException(parsedCommand.getOuterId());
            } else {
                dbServiceGood.delete(existingGood.get());
            }
        }
    }

    private Optional<AppUser> findUser(User user) {
        return dbServiceAppUser.findByTelegramId(user.getId());
    }
}
