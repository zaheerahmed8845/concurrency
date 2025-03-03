package com.example.completable.future.parallelism.domain;

import com.example.completable.future.parallelism.util.DelayUtil;
import com.example.completable.future.parallelism.util.RandomUtil;
import lombok.Data;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Data
public class Shop {

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        DelayUtil.delay();
        return RandomUtil.getRandom().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
