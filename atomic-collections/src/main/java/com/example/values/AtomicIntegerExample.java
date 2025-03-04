package com.example.values;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {

    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> counter.incrementAndGet());
        Thread t2 = new Thread(() -> counter.incrementAndGet());

        t1.start();
        t2.start();
    }
}
