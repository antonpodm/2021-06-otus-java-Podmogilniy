package ru.otus.controllers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("RestService")
public class RestService {

    public String getRequestData(String id) throws IOException {
        Document doc = Jsoup.connect("https://www.originsro-market.de/sells/item_id/" + id).get();
        doc.select("p").forEach(System.out::println);
        var sell = doc.body().getElementById("sell");
        var children = sell.children();
        var averagePrices = children.get(0);
        var averageLastWeek = averagePrices.getElementsContainingOwnText("Average last week");
      //  -----------------
        averageLastWeek.forEach(element->System.out.println(element.ownText()));
      //  -----------------

        var currentOffers = sell.getElementById("table4");
        var offersList = currentOffers.getElementsByTag("tr");
        offersList.forEach(System.out::println);
        return sell.toString();
    }


}
