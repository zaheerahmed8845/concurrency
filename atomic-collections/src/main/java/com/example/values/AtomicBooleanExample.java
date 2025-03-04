package com.example.values;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanExample {

    private static AtomicBoolean flag = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            if (flag.compareAndSet(false, true)) {
                System.out.println("Thread 1 changed flag to true");
            }
        });

        Thread t2 = new Thread(() -> {
            if (flag.compareAndSet(false, true)) {
                System.out.println("Thread 2 changed flag to true");
            } else {
                System.out.println("Thread 2 found flag already true");
            }
        });

        t1.start();
        t2.start();
    }
}
