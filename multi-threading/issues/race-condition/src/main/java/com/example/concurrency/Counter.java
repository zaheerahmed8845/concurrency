package com.example.concurrency;

public class Counter {

    private int count = 0;

    public void increment() {
        count++;
        try {
            Thread.sleep(1); // Artificial delay to increase race condition occurrence
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int get() {
        return count;
    }
}
