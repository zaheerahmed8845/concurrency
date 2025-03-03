package com.example.completable.future.pipelining;

import com.example.completable.future.pipelining.domain.Shop;
import com.example.completable.future.pipelining.service.PriceService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {

        List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),
                new Shop("BigBazaar"));

        PriceService priceService = new PriceService(shops);
        long start = System.nanoTime();
        System.out.println(priceService.findPricesUsingCompletableFuture("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Stream : done in " + duration + " msecs");

    }
}