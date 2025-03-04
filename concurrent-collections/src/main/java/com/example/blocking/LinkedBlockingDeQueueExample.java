package com.example.blocking;

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDeQueueExample {

    public static void main(String[] args) throws InterruptedException {
        fifo();
        lifo();
    }

    private static void fifo() throws InterruptedException {
        // Create an optionally bounded deque (max 5 elements)
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(5);

        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    deque.putLast(i); // Enqueue at the tail
                    System.out.println("Produced: " + i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    int item = deque.takeFirst(); // Dequeue from the head
                    System.out.println("Consumed: " + item);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        /*  O/P:
            Produced: 1
            Produced: 2
            Consumed: 1
            Produced: 3
            Produced: 4
            Consumed: 2
            Produced: 5
            Consumed: 3
            Consumed: 4
            Consumed: 5*/
    }

    private static void lifo() throws InterruptedException {
        LinkedBlockingDeque<String> stack = new LinkedBlockingDeque<>();

        stack.putFirst("A"); // Push at front
        stack.putFirst("B");
        stack.putFirst("C");

        System.out.println(stack.takeFirst()); // C
        System.out.println(stack.takeFirst()); // B
        System.out.println(stack.takeFirst()); // A

        /* O/P:
        *   C
            B
            A*/
    }


}
