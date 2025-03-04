package com.example.non.blocking;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {

    public static void main(String[] args) {

        regExample();
        prodConsExample();
    }

    private static void regExample() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Adding elements
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        System.out.println("Map: " + map);

        // Retrieving a value
        System.out.println("Value of B: " + map.get("B"));

        // Checking if a key exists
        System.out.println("Contains C? " + map.containsKey("C"));

        // Removing an element
        map.remove("A");
        System.out.println("After removal: " + map);
    }

    private static void prodConsExample() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Writer Thread
        Thread writer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                map.put("Key" + i, i);
                System.out.println("Added: Key" + i);
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
