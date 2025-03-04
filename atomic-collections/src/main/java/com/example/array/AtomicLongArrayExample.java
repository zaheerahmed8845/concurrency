package com.example.array;

import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicLongArrayExample {

    private static AtomicLongArray atomicArray = new AtomicLongArray(new long[]{10L, 20L, 30L});

    public static void main(String[] args) {
        boolean updated = atomicArray.compareAndSet(0, 10L, 50L);  // Update index 0 from 10 → 50
        System.out.println("Updated? " + updated + ", New Value: " + atomicArray.get(0));

        updated = atomicArray.compareAndSet(0, 10L, 100L); // Fails (value is now 50)
        System.out.println("Updated? " + updated + ", New Value: " + atomicArray.get(0));
    }
}
