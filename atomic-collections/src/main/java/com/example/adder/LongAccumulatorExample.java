package com.example.adder;

import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorExample {
    public static void main(String[] args) {
        // LongAccumulator with a function to compute max value
        LongAccumulator maxAccumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);

        maxAccumulator.accumulate(10); // Max so far: 10
        maxAccumulator.accumulate(5);  // Max remains 10
        maxAccumulator.accumulate(20); // Max updated to 20

        System.out.println("Maximum Value: " + maxAccumulator.get()); // Output: 20
    }


}
