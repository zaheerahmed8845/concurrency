package com.example.copyonwritearray;

import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetExample {

    public static void main(String[] args) {
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        set.add(1);
        set.add(2);
        set.add(3);

        // Thread 1: Iterates over the set
        Thread reader = new Thread(() -> {
            for (Integer num : set) {
                System.out.println("Reading: " + num);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        // Thread 2: Modifies the set
        Thread writer = new Thread(() -> {
            set.add(4);
            System.out.println("Added: 4");
        });

        reader.start();
        writer.start();
    }

}
