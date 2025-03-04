package com.example.values;


import java.util.concurrent.atomic.AtomicReference;

class MyObject {
    String name;

    MyObject(String name) {
        this.name = name;
    }
}

public class AtomicReferenceExample {
    private static AtomicReference<MyObject> atomicRef = new AtomicReference<>(new MyObject("Old"));

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> atomicRef.set(new MyObject("New")));
        Thread t2 = new Thread(() -> atomicRef.set(new MyObject("Another")));

        t1.start();
        t2.start();
    }

}
