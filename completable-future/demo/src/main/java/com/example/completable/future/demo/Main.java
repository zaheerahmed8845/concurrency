package com.example.completable.future.demo;

import com.example.completable.future.demo.domain.Shop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");

        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsyncComp("my favorite price");
        long invocationTime = ((System.nanoTime() - start)) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after : " + retrievalTime + " msecs");
    }
}