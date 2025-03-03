package com.example;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> task1());
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> task2());

        CompletableFuture<Integer> future3 = future1.thenCombine(future2, (v1, v2) -> sum(v1, v2));

        System.out.println("Result : " + future3.join());

    }

    private static Integer sum(Integer v1, Integer v2) {
        return v1 + v2;
    }

    private static Integer task1() {
        System.out.println(Thread.currentThread().getName() + " : Task 1" + " Current time : " + System.currentTimeMillis());
        return 1;
    }

    private static Integer task2() {
        System.out.println(Thread.currentThread().getName() + " : Task 2" + " Current time : " + System.currentTimeMillis());
        return 2;
    }
}