package com.example.blocking;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;


class Task {
    String name;
    int priority;

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return name + " (Priority: " + priority + ")";
    }
}


public class PriorityBlockingQueueExample {

    public static void main(String[] args) throws InterruptedException {
        regExample();
        prodConsExample();
    }

    private static void regExample() throws InterruptedException {
        // Creating a PriorityBlockingQueue with natural ordering
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        // Adding elements
        queue.add(40);
        queue.add(10);
        queue.add(30);
        queue.add(20);

        // Removing elements (based on priority, i.e., natural ordering)
        System.out.println(queue.take()); // 10 (lowest number)
        System.out.println(queue.take()); // 20
        System.out.println(queue.take()); // 30
        System.out.println(queue.take()); // 40
    }

    private static void prodConsExample() throws InterruptedException {
        // Custom Comparator: Higher priority first
        PriorityBlockingQueue<Task> taskQueue = new PriorityBlockingQueue<>(10, Comparator.comparingInt(t -> -t.priority));

        taskQueue.add(new Task("Low Priority Task", 1));
        taskQueue.add(new Task("Medium Priority Task", 2));
        taskQueue.add(new Task("High Priority Task", 3));

        System.out.println(taskQueue.take()); // High Priority Task
        System.out.println(taskQueue.take()); // Medium Priority Task
        System.out.println(taskQueue.take()); // Low Priority Task
    }


}
