package com.example;

import com.example.domain.SharedClass;

public class Main {

    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRaces();
            }
        });

        t1.start();
        t2.start();
    }
}