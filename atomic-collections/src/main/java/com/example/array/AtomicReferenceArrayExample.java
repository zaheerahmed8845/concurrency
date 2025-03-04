package com.example.array;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class AtomicReferenceArrayExample {

    private static AtomicReferenceArray<String> atomicArray = new AtomicReferenceArray<>(new String[]{"Apple", "Banana", "Cherry"});

    public static void main(String[] args) {
        boolean updated = atomicArray.compareAndSet(0, "Apple", "Grapes"); // Update index 0 from Apple → Grapes
        System.out.println("Updated? " + updated + ", New Value: " + atomicArray.get(0));

        updated = atomicArray.compareAndSet(0, "Apple", "Orange"); // Fails (value is now Grapes)
        System.out.println("Updated? " + updated + ", New Value: " + atomicArray.get(0));
    }
}
