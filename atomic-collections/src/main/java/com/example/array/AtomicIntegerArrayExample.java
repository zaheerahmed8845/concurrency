package com.example.array;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayExample {

    private static AtomicIntegerArray atomicArray = new AtomicIntegerArray(new int[]{10, 20, 30});

    public static void main(String[] args) {
        boolean updated = atomicArray.compareAndSet(0, 10, 50);  // Update index 0 from 10 → 50
        System.out.println("Updated? " + updated + ", New Value: " + atomicArray.get(0));

        updated = atomicArray.compareAndSet(0, 10, 100); // Fails (value is now 50)
        System.out.println("Updated? " + updated + ", New Value: " + atomicArray.get(0));
    }

}
