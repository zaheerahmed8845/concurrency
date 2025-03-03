package com.example.completable.future.pipelining.util;

public class DelayUtil {

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
