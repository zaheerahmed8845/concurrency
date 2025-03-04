package com.example.values;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongExample {
    private static AtomicLong counter = new AtomicLong(0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> counter.incrementAndGet());
        Thread t2 = new Thread(() -> counter.incrementAndGet());

        t1.start();
        t2.start();
    }

}
