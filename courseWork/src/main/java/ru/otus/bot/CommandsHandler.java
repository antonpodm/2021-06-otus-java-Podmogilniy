package ru.otus.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.otus.bot.parser.ParsedAddCommand;
import ru.otus.bot.parser.ParsedInfoCommand;
import ru.otus.bot.parser.ParsedRemoveCommand;
import ru.otus.crm.model.AppUser;
import ru.otus.crm.model.Good;
import ru.otus.crm.service.DBServiceGood;
import ru.otus.crm.service.DBServiceAppUser;
import ru.otus.crm.service.DBServiceGoodInfo;
import ru.otus.dto.GoodDto;
import ru.otus.dto.GoodInfoDto;
import ru.otus.enums.Commands;
import ru.otus.exceptions.AppUserNotFoundException;
import ru.otus.exceptions.CommandAlreadyDoneException;
import ru.otus.exceptions.GoodNotFoundException;
import ru.otus.exceptions.GoodsInfoNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class CommandsHandler {

    private final DBServiceGood dbServiceGood;
    private final DBServiceGoodInfo dbServiceGoodInfo;
    private final DBServiceAppUser dbServiceAppUser;

    public void handleStartCommand(User tgUser, Chat chat) {
        var userOptional = findUser(tgUser);
        AppUser.AppUserBuilder builder;

        if (userOptional.isPresent()) {
            var appUser = userOptional.get();
            if (appUser.isActive()) {
                throw new CommandAlreadyDoneException(Commands.START.getCommand());
            }
            builder = appUser.toBuilder();
        } else {
            builder = AppUser.builder().telegramId(tgUser.getId());
        }

        var appUser = builder.firstName(tgUser.getFirstName())
                .chatId(chat.getId())
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
                throw new CommandAlreadyDoneException(Commands.STOP.getCommand());
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

    public String handleListCommand(User tgUser) {
        var userOptional = findUser(tgUser);
        if (userOptional.isEmpty()) {
            throw new AppUserNotFoundException(tgUser.getId());
        } else {
            var appUser = userOptional.get();
            var existingGoods = dbServiceGood.findByUserId(appUser.getId());
            if (existingGoods.isEmpty()) {
                throw new GoodNotFoundException(-1L);
            }
            var joiner = new StringJoiner("\n");
            var goodsDto = existingGoods.stream().map(GoodDto::new).map(GoodDto::toString).toList();
            for (String goodString : goodsDto) {
                joiner.add(goodString);
            }
            return joiner.toString();
        }
    }

    public void handleDeleteUserCommand(User tgUser) {
        var userOptional = findUser(tgUser);
        if (userOptional.isEmpty()) {
            throw new AppUserNotFoundException(tgUser.getId());
        } else {
            var appUser = userOptional.get();
            dbServiceAppUser.delete(appUser);
        }
    }


    public String handleInfoCommand(User tgUser, String[] strings) {
        var userOptional = findUser(tgUser);
        if (userOptional.isEmpty()) {
            throw new AppUserNotFoundException(tgUser.getId());
        } else {
            var parsedCommand = new ParsedInfoCommand(strings);
            var existingGoodsInfo = dbServiceGoodInfo.findByOuterId(parsedCommand.getOuterId());
            if (existingGoodsInfo.isEmpty()) {
                throw new GoodsInfoNotFoundException(parsedCommand.getOuterId());
            }
            return new GoodInfoDto(existingGoodsInfo.get()).toString();
        }
    }

    private Optional<AppUser> findUser(User user) {
        return dbServiceAppUser.findByTelegramId(user.getId());
    }
}
