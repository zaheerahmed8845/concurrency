package com.example.completable.future.parallelism;

import com.example.completable.future.parallelism.domain.Shop;
import com.example.completable.future.parallelism.service.PriceService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class Main {
    public static void main(String[] args) {

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available Processor : " + availableProcessors);

        int totalShops = availableProcessors + 1;
        System.out.println("Total Shops : " + totalShops);

        List<Shop> shops = new ArrayList<>();

        IntStream.range(0, totalShops).
                forEach(i -> shops.add(new Shop("Shop-" + i)));

        computeUsingStream(shops);

        computeUsingParallelStream(shops);

        computeUsingCompletableFuture(shops);

        computeUsingCompletableFutureUsingExecutor(shops);

        computeUsingCompletableFutureUsingVirtualThreadExecutor(shops);
    }

    private static void computeUsingStream(List<Shop> shops) {
        PriceService priceService = new PriceService(shops);
        long start = System.nanoTime();
        System.out.println(priceService.findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Stream : done in " + duration + " msecs");
    }

    private static void computeUsingParallelStream(List<Shop> shops) {
        PriceService priceService = new PriceService(shops);
        long start = System.nanoTime();
        System.out.println(priceService.findPricesUsingParallelStream("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Parallel Stream : done in " + duration + " msecs");
    }

    private static void computeUsingCompletableFuture(List<Shop> shops) {
        PriceService priceService = new PriceService(shops);
        long start = System.nanoTime();
        System.out.println(priceService.findPricesUsingCompletableFuture("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Completable Future : done in " + duration + " msecs");
    }

    private static void computeUsingCompletableFutureUsingExecutor(List<Shop> shops) {
        PriceService priceService = new PriceService(shops);
        long start = System.nanoTime();
        System.out.println(priceService.findPricesUsingCompletableFutureUsingExecutor("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Completable Future Using Executor : done in " + duration + " msecs");
    }

    private static void computeUsingCompletableFutureUsingVirtualThreadExecutor(List<Shop> shops) {
        PriceService priceService = new PriceService(shops);
        long start = System.nanoTime();
        System.out.println(priceService.findPricesUsingCompletableFutureUsingVirtualThreadExecutor("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Completable Future Using Virtual Thread Executor : done in " + duration + " msecs");
    }
}