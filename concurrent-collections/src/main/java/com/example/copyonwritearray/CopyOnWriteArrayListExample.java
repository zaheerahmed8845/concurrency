package com.example.copyonwritearray;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {

    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // Thread 1: Iterates over the list
        Thread reader = new Thread(() -> {
            for (Integer num : list) {
                System.out.println("Reading: " + num);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        // Thread 2: Modifies the list
        Thread writer = new Thread(() -> {
            list.add(4);
            System.out.println("Added: 4");
        });

        reader.start();
        writer.start();
    }

}
