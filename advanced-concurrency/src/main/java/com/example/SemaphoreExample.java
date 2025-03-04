package com.example;

import java.util.concurrent.Semaphore;

class SharedResource {
    private final Semaphore semaphore = new Semaphore(2); // Allow max 2 threads

    public void accessResource(String threadName) {
        try {
            semaphore.acquire(); // Acquire permit
            System.out.println(threadName + " accessing resource...");
            Thread.sleep(2000); // Simulate work
            System.out.println(threadName + " releasing resource...");
        } catch (InterruptedException ignored) {
        } finally {
            semaphore.release(); // Release permit
        }
    }
}

public class SemaphoreExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        for (int i = 1; i <= 5; i++) {
            final String threadName = "Thread-" + i;
            new Thread(() -> resource.accessResource(threadName)).start();
        }
    }
}
