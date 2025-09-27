package com.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            numbers.add(i);
        }

        // Thread 1: Iterates over the list
        Thread t1 = new Thread(() -> {
            for (Integer num : numbers) { // May throw ConcurrentModificationException
                System.out.println("Reading: " + num);
                try {
                    Thread.sleep(10); // Simulate delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2: Modifies the list
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(20); // Ensure modification happens while iteration is in progress
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numbers.add(6); // Modifying the list while another thread is iterating
            System.out.println("Added 6 to the list.");
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}