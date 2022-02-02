package ru.otus.coursework.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.coursework.bot.Bot;
import ru.otus.coursework.crm.model.*;
import ru.otus.coursework.crm.service.DBServiceAppUser;
import ru.otus.coursework.crm.service.DBServiceGood;
import ru.otus.coursework.crm.service.DBServiceGoodInfo;
import ru.otus.coursework.crm.service.DBServiceSentData;
import ru.otus.coursework.dto.MessageDto;
import ru.otus.coursework.dto.MessagesDto;
import ru.otus.coursework.dto.ShopDto;
import ru.otus.coursework.enums.MathStatement;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SendDataService {

    private static final Logger log = LoggerFactory.getLogger(SendDataService.class);
    private static final Executor threadPool = Executors.newFixedThreadPool(20);

    private final DBServiceAppUser dbServiceAppUser;
    private final DBServiceGood dbServiceGood;
    private final DBServiceGoodInfo dbServiceGoodInfo;
    private final DBServiceSentData dbServiceSentData;
    private final Bot bot;

    public void prepareGoodsDataAndSend() throws InterruptedException {
        var usersGoods = loadUsersGoods();
        var goodsIds = findGoodsIds(usersGoods);
        var goodsInfo = loadGoodsInfo(goodsIds);
        sendGoodsDataToUsers(usersGoods, goodsInfo);
    }

    private Map<AppUser, List<Good>> loadUsersGoods() throws InterruptedException {
        var usersGoods = new ConcurrentHashMap<AppUser, List<Good>>();

        var users = dbServiceAppUser.findByActive(true);
        var latch = new CountDownLatch(users.size());

        users.forEach(user -> {
            CompletableFuture.runAsync(() -> {
                try {
                    var goods = dbServiceGood.findByUserId(user.getId());
                    usersGoods.put(user, goods);
                } catch (Exception ex) {
                    log.info("loading users goods {} ", user, ex);
                } finally {
                    latch.countDown();
                }
            }, threadPool);
        });

        latch.await();

        return usersGoods;
    }

    private Set<Long> findGoodsIds(Map<AppUser, List<Good>> usersGoods) {
        Set<Long> goodsIds = ConcurrentHashMap.newKeySet();
        usersGoods
                .values()
                .forEach(
                        goods -> goodsIds
                                .addAll(
                                        goods
                                                .stream()
                                                .map(Good::getOuterId)
                                                .toList()
                                )
                );
        return goodsIds;
    }

    private Map<Long, GoodInfo> loadGoodsInfo(Set<Long> goodsIds) throws InterruptedException {
        var goodsInfo = new ConcurrentHashMap<Long, GoodInfo>();

        var latch = new CountDownLatch(goodsIds.size());

        goodsIds.forEach(id -> {
            CompletableFuture.runAsync(() -> {
                try {
                    var goodInfoOptional = dbServiceGoodInfo.findByOuterId(id);
                    if (goodInfoOptional.isPresent()) {
                        var good = goodInfoOptional.get();
                        goodsInfo.put(id, good);
                    }
                } catch (Exception ex) {
                    log.info("loadGoodsInfo {} ", id, ex);
                } finally {
                    latch.countDown();
                }
            }, threadPool);
        });

        latch.await();

        return goodsInfo;
    }

    private void sendGoodsDataToUsers(Map<AppUser, List<Good>> usersGoods, Map<Long, GoodInfo> goodsInfo) throws InterruptedException {
        var latch = new CountDownLatch(usersGoods.size());

        usersGoods.entrySet()
                .forEach(entry -> {
                    CompletableFuture.runAsync(() -> {
                        try {
                            sendGoodDataToUser(entry.getKey(), entry.getValue(), goodsInfo);
                        } catch (Exception ex) {
                            log.info("sendGoodDataToUser {} ", entry, ex);
                        } finally {
                            latch.countDown();
                        }
                    }, threadPool);
                });

        latch.await();
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
