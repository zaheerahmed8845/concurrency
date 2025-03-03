package com.example.completable.future.pipelining.service;

import com.example.completable.future.pipelining.domain.Discount;
import com.example.completable.future.pipelining.domain.Quote;
import com.example.completable.future.pipelining.util.DelayUtil;

public class DiscountService {

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " +
                DiscountService.apply(quote.getPrice(),
                        quote.getDiscountCode());
    }

    private static double apply(double price, Discount.Code code) {
        DelayUtil.delay();
        return price * (100 - code.percentage) / 100;
    }
}
