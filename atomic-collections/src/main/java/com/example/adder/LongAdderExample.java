package com.example.adder;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderExample {

    private static LongAdder counter = new LongAdder();

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                counter.increment(); // Faster than AtomicLong in high contention
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
    }
}
