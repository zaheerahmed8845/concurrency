package com.example.completable.future.parallelism.util;

public class DelayUtil {

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
