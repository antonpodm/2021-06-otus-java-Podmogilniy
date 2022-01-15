package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.bot.Bot;
import ru.otus.crm.model.*;
import ru.otus.crm.service.DBServiceAppUser;
import ru.otus.crm.service.DBServiceGood;
import ru.otus.crm.service.DBServiceGoodInfo;
import ru.otus.crm.service.DBServiceSentData;
import ru.otus.dto.MessageDto;
import ru.otus.dto.MessagesDto;
import ru.otus.dto.ShopDto;
import ru.otus.enums.MathStatement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SendDataService {

    private static final Logger log = LoggerFactory.getLogger(SendDataService.class);

    private final DBServiceAppUser dbServiceAppUser;
    private final DBServiceGood dbServiceGood;
    private final DBServiceGoodInfo dbServiceGoodInfo;
    private final DBServiceSentData dbServiceSentData;
    private final Bot bot;

    public void sendGoodsDataToUsers() {
        var users = dbServiceAppUser.findByActive(true);
        var usersGoods = new ConcurrentHashMap<AppUser, List<Good>>();
        var goodsIds = new HashSet<Long>();

        for (var user : users) {
            var goods = dbServiceGood.findByUserId(user.getId());
            usersGoods.put(user, goods);
            goodsIds.addAll(goods.stream().map(Good::getOuterId).toList());
        }

        var goodsInfos = new ConcurrentHashMap<Long, GoodInfo>();

        for (var id : goodsIds) {
            var goodInfoOptional = dbServiceGoodInfo.findByOuterId(id);
            if (goodInfoOptional.isPresent()) {
                var good = goodInfoOptional.get();
                goodsInfos.put(good.getOuterId(), good);
            }
        }

        for (var user : usersGoods.keySet()) {
            try {
                sendGoodDataToUser(user, usersGoods.get(user), goodsInfos);
            } catch (Exception ex) {
                log.error("sendGoodDataToUser", ex);
            }
        }
    }

    private void sendGoodDataToUser(AppUser user, List<Good> userGoods, Map<Long, GoodInfo> goodInfos) {
        List<String> messages = new ArrayList<>();
        for (var good : userGoods) {
            var message = makeGoodMessage(good, goodInfos.get(good.getOuterId()));
            if (message != null) {
                messages.add(message);
            }
        }

        var newMessage = new MessagesDto(messages).toString();
        var lastMessage = dbServiceSentData.findByUserIdLimit1(user.getId());
        if (lastMessage.isEmpty() || !lastMessage.get().getMessage().equals(newMessage)) {
            if (!newMessage.isEmpty()) {
                bot.sendToUser(user, newMessage);

                var data = SentData.builder()
                        .userId(user.getId())
                        .message(newMessage)
                        .build();

                dbServiceSentData.save(data);
            }
        }
    }

    private String makeGoodMessage(Good good, GoodInfo goodInfo) {
        String message = null;

        var dealType = good.getDealType();
        var price = good.getPrice();
        var statement = good.getMathStatement();

        var shopsByDealType = goodInfo.getShops()
                .stream()
                .filter(shop -> shop.getDealType().equals(dealType))
                .toList();

        var shopsByPrice = new ArrayList<Shop>();

        for (Shop shop : shopsByDealType) {
            if (statement.equals(MathStatement.LESS)) {
                if (shop.getPrice() < price) {
                    shopsByPrice.add(shop);
                }
            } else {
                if (shop.getPrice() > price) {
                    shopsByPrice.add(shop);
                }
            }
        }

        if (!shopsByPrice.isEmpty()) {
            message = new MessageDto(
                    goodInfo.getName(),
                    dealType,
                    shopsByPrice
                            .stream()
                            .map(ShopDto::new)
                            .collect(Collectors.toList()))
                    .toString();
        }

        return message;
    }
}
