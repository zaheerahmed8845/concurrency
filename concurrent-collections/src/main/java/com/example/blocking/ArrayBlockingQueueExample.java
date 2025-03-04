package com.example.blocking;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueExample {

    public static void main(String[] args) throws InterruptedException {
        regExample();
        prodConsExample();
    }

    private static void regExample() throws InterruptedException {
        // Create a bounded queue with capacity of 3
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        // Adding elements
        queue.put(1);
        queue.put(2);
        queue.put(3);

        System.out.println("Queue after inserts: " + queue);

        // Removing elements
        System.out.println("Removed: " + queue.take()); // Removes 1
        System.out.println("Removed: " + queue.take()); // Removes 2

        // Adding more elements
        queue.put(4);
        System.out.println("Queue after more inserts: " + queue);
    }

    private static void prodConsExample() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Producer Thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                    Thread.sleep(500);  // Simulating delay
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Consumer Thread
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    int data = queue.take();
                    System.out.println("Consumed: " + data);
                    Thread.sleep(1000); // Simulating delay
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();
    }

}
