package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        System.out.println("Running using the same thread");
        CompletableFuture.supplyAsync(() -> task1())
                .thenApply((str) -> task2())
                .thenCompose((str) -> CompletableFuture.supplyAsync(() -> task3()))
                .thenAccept((str) -> task4())
                .join();

        System.out.println("Running each task using different thread");
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletableFuture.supplyAsync(() -> task1(), executor)
                .thenApplyAsync((str) -> task2(), executor)
                .thenComposeAsync((str) -> CompletableFuture.supplyAsync(() -> task3()), executor)
                .thenAcceptAsync((str) -> task4(), executor)
                .join();

        executor.shutdown();
    }

    private static String task1() {
        System.out.println(Thread.currentThread().getName() + " : Task 1" + " Current time : " + System.currentTimeMillis());
        return "Task 1";
    }

    private static String task2() {
        delay();
        System.out.println(Thread.currentThread().getName() + " : Task 2" + " Current time : " + System.currentTimeMillis());
        return "Task 2";
    }

    private static String task3() {
        System.out.println(Thread.currentThread().getName() + " : Task 3" + " Current time : " + System.currentTimeMillis());
        return "Task 3";
    }

    private static void task4() {
        System.out.println(Thread.currentThread().getName() + " : Task 4" + " Current time : " + System.currentTimeMillis());
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}