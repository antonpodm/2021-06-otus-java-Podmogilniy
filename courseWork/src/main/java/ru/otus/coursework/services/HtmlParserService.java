package ru.otus.coursework.services;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.coursework.config.AppConfig;
import ru.otus.coursework.services.pages.BuyPage;
import ru.otus.coursework.services.pages.PageType;
import ru.otus.coursework.crm.model.GoodInfo;
import ru.otus.coursework.crm.model.Shop;
import ru.otus.coursework.crm.service.DBServiceGoodInfo;
import ru.otus.coursework.services.pages.SellPage;
import ru.otus.coursework.dto.enums.DealType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HtmlParserService {

    private static final Logger log = LoggerFactory.getLogger(HtmlParserService.class);
    private static final String averageLastWeekString = "Average last week: ";

    private final AppConfig appConfig;
    private final DBServiceGoodInfo dbServiceGoodInfo;
    private final BuyPage buyPage;
    private final SellPage sellPage;

    public void loadGoodData(long id) throws IOException {
        var goodInfoOptional = dbServiceGoodInfo.findByOuterId(id);

        GoodInfo.GoodInfoBuilder goodInfo;
        if (goodInfoOptional.isPresent()) {
            goodInfo = goodInfoOptional.get().toBuilder();
        } else {
            goodInfo = GoodInfo.builder().outerId(id);
        }

        Document doc = Jsoup.connect(appConfig.getUrl() + "/" + id).get();
        try {
            goodInfo.name(findName(doc));
        } catch (NullPointerException ex) {
            log.error("name", ex);
        }
        addAveragePrice(goodInfo, doc, sellPage);
        addAveragePrice(goodInfo, doc, buyPage);

        var shops = new HashSet<Shop>();
        shops.addAll(findShops(doc, sellPage));
        shops.addAll(findShops(doc, buyPage));

        dbServiceGoodInfo.save(goodInfo.shops(shops).build());
    }

    private Element getPageByType(Document document, PageType pageType) {
        return document.body().getElementById(pageType.getDealType().name().toLowerCase());
    }

    private String findName(Document document) {
        var container = document
                .body()
                .getElementsByClass("container").last();
        var row = container.getElementsByClass("row").first();
        var span = row.getElementsByTag("span").first();
        return span.ownText();
    }

    private void addAveragePrice(GoodInfo.GoodInfoBuilder goodInfo, Document document, PageType pageType) {
        var page = getPageByType(document, pageType);
        var averagePrices = page.children().first();

        var averageLastWeek = averagePrices.getElementsContainingOwnText(averageLastWeekString).first();
        var text = replaceComma(averageLastWeek.ownText().split(averageLastWeekString)[1]);
        if (pageType.getDealType().equals(DealType.BUY)) {
            goodInfo.buyAveragePrice(Long.parseLong(text));
        } else {
            goodInfo.sellAveragePrice(Long.parseLong(text));
        }
    }

    private Set<Shop> findShops(Document document, PageType pageType) {
        var page = getPageByType(document, pageType);
        var currentOffers = page.getElementById(pageType.getTableName());
        var table = currentOffers.getElementsByClass("table");
        var offerListBody = table.first().getElementsByTag("tbody").first();

        var offersList = offerListBody.getElementsByTag("tr");

        var shops = new HashSet<Shop>();
        for (Element element : offersList) {
            var amount = element.getElementsByClass("Amount").first();
            var price = element.getElementsByClass("Price").first();
            var shop =
                    Shop.builder()
                            .amount(Integer.valueOf(replaceComma(amount.ownText())))
                            .price(Long.valueOf(replaceComma(price.ownText())))
                            .dealType(pageType.getDealType())
                            .build();
            shops.add(shop);
        }
        return shops;
    }

    private String replaceComma(String string) {
        return string.replace(",", "");
    }


}
