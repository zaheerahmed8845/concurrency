package com.example;

import com.example.concurrency.Counter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        var counter = new Counter();

        Thread t1 = Thread.ofPlatform()
                .name("Counter-Increment-Thread")
                .start(() -> {
                    for (int i = 0; i < 1000; i++) {
                        counter.increment();
                    }
                });

        Thread t2 = Thread.ofPlatform()
                .name("Counter-Decrement-Thread")
                .start(() -> {
                    for (int i = 0; i < 1000; i++) {
                        counter.increment();
                    }
                });

        t1.join();
        t2.join();

        log.info("Counter value : {}", counter.get());
    }
}
