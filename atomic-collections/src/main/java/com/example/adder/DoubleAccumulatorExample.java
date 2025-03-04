package com.example.adder;

import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.function.DoubleBinaryOperator;

public class DoubleAccumulatorExample {

    public static void main(String[] args) {
        // Define an accumulator function for addition
        DoubleBinaryOperator addFunction = Double::sum;

        // Create DoubleAccumulator with identity value 0.0
        DoubleAccumulator accumulator = new DoubleAccumulator(addFunction, 0.0);

        accumulator.accumulate(10.5);
        accumulator.accumulate(5.3);
        accumulator.accumulate(3.2);

        System.out.println("Total Sum: " + accumulator.get()); // Output: 19.0
    }

}
