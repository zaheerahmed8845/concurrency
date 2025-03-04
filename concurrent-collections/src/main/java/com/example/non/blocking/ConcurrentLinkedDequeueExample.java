package com.example.non.blocking;

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeueExample {

    public static void main(String[] args) {

        regExample();
        prodConsExample();
    }

    private static void regExample() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();

        // Add elements to both ends
        deque.addFirst(10);
        deque.addLast(20);
        deque.offerFirst(30);
        deque.offerLast(40);

        System.out.println("Deque: " + deque); // Output: [30, 10, 20, 40]

        // Remove elements from both ends
        System.out.println("Remove First: " + deque.pollFirst()); // Output: 30
        System.out.println("Remove Last: " + deque.pollLast());   // Output: 40

        System.out.println("Updated Deque: " + deque); // Output: [10, 20]
    }

    private static void prodConsExample() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();

        // Producer Thread (Adding elements)
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                deque.offerFirst(i);
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
                Integer value = deque.pollLast();
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
