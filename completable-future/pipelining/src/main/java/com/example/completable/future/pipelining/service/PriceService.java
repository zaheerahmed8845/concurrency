package com.example.completable.future.pipelining.service;

import com.example.completable.future.pipelining.domain.Quote;
import com.example.completable.future.pipelining.domain.Shop;
import com.example.completable.future.pipelining.util.ExecutorUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class PriceService {

    private List<Shop> shops;

    public PriceService(List<Shop> shops) {
        this.shops = shops;
    }

    public List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(DiscountService::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesUsingCompletableFuture(String product) {
        Executor executor = ExecutorUtil.getExecutor(shops);
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getPrice(product), executor))
                        .map(future -> future.thenApply(str -> Quote.parse(str)))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(
                                        () -> DiscountService.applyDiscount(quote), executor
                                )))
                        .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
