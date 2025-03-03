package com.example.completable.future.pipelining.domain;

public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        public final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }
}
