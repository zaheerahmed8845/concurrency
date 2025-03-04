package com.example.non.blocking;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapExample {

    public static void main(String[] args) {

        regExample();
        prodConsExample();
    }

    private static void regExample() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Adding elements
        map.put(3, "Three");
        map.put(1, "One");
        map.put(2, "Two");
        map.put(5, "Five");
        map.put(4, "Four");

        System.out.println("Sorted Map: " + map);

        // Accessing elements
        System.out.println("First Key: " + map.firstKey());  // 1
        System.out.println("Last Key: " + map.lastKey());    // 5

        // Navigating the map
        System.out.println("Higher Entry for 2: " + map.higherEntry(2));  // 3=Three
        System.out.println("Ceiling Entry for 3: " + map.ceilingEntry(3)); // 3=Three
    }

    private static void prodConsExample() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Writer Thread
        Thread writer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                map.put(i, "Value" + i);
                System.out.println("Added: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        // Reader Thread
        Thread reader = new Thread(() -> {
            while (true) {
                System.out.println("Current Map: " + map);
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
