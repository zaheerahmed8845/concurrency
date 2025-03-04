package com.example.adder;

import java.util.concurrent.atomic.DoubleAdder;

public class DoubleAdderExample {
    private static DoubleAdder counter = new DoubleAdder();

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                counter.add(1.0); // Faster than AtomicReference<Double>
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
    }
}
