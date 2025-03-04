package com.example.non.blocking;

import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetExample {

    public static void main(String[] args) {

        regExample();
        prodConsExample();
    }

    private static void regExample() {
        ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();

        // Adding elements
        set.add(5);
        set.add(2);
        set.add(8);
        set.add(1);

        System.out.println("Sorted Set: " + set); // Output: [1, 2, 5, 8]

        // Removing an element
        set.remove(2);
        System.out.println("After Removal: " + set); // Output: [1, 5, 8]

        // Checking elements
        System.out.println("Contains 5? " + set.contains(5)); // Output: true
    }

    private static void prodConsExample() {
        ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();

        // Writer Thread (Adding elements)
        Thread writer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                set.add(i);
                System.out.println("Added: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        // Reader Thread (Checking elements)
        Thread reader = new Thread(() -> {
            while (true) {
                System.out.println("Current Set: " + set);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
        });

        writer.start();
        reader.start();
    }

}
