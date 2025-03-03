package com.example.completable.future.pipelining.domain;

import com.example.completable.future.pipelining.util.DelayUtil;
import com.example.completable.future.pipelining.util.RandomUtil;
import lombok.Data;

@Data
public class Shop {

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[
                RandomUtil.getRandom().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    private double calculatePrice(String product) {
        DelayUtil.delay();
        return RandomUtil.getRandom().nextDouble() * product.charAt(0) + product.charAt(1);
    }
}
