package com.example.non.blocking;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExample {

    public static void main(String[] args) {

        regExample();
        prodConsExample();
    }

    private static void regExample() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        // Add elements
        queue.offer(10);
        queue.offer(20);
        queue.offer(30);

        // Peek (retrieves but does not remove)
        System.out.println("Peek: " + queue.peek()); // Output: 10

        // Poll (retrieves and removes)
        System.out.println("Poll: " + queue.poll()); // Output: 10
        System.out.println("Poll: " + queue.poll()); // Output: 20

        // Remaining elements
        System.out.println("Queue: " + queue); // Output: [30]
    }

    private static void prodConsExample() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        // Producer Thread (Adding elements)
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                queue.offer(i);
                System.out.println("Produced: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        // Consumer Thread (Removing elements)
        Thread consumer = new Thread(() -> {
            while (true) {
                Integer value = queue.poll();
                if (value != null) {
                    System.out.println("Consumed: " + value);
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                }
            }
        });

        producer.start();
        consumer.start();
    }

}
